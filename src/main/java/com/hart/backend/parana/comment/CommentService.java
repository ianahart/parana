package com.hart.backend.parana.comment;

import com.hart.backend.parana.comment.dto.CommentDto;
import com.hart.backend.parana.comment.dto.CommentPaginationDto;
import com.hart.backend.parana.comment.request.CreateCommentRequest;
import com.hart.backend.parana.commentlike.CommentLikeService;
import com.hart.backend.parana.connection.ConnectionService;

import java.time.Instant;
import java.util.List;

import com.hart.backend.parana.advice.ForbiddenException;
import com.hart.backend.parana.advice.BadRequestException;
import com.hart.backend.parana.advice.NotFoundException;

import com.hart.backend.parana.post.Post;
import com.hart.backend.parana.post.PostService;
import com.hart.backend.parana.replycomment.ReplyCommentService;
import com.hart.backend.parana.user.Role;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.util.MyUtil;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    Logger logger = LoggerFactory.getLogger(CommentService.class);
    private final int COMMENT_LIMIT = 5;
    private final int COMMENT_LIMIT_IN_SECONDS = 60 * 5;

    private final CommentRepository commentRepository;

    private final UserService userService;

    private final PostService postService;

    private final ConnectionService connectionService;

    private final CommentLikeService commentLikeService;

    private final ReplyCommentService replyCommentService;

    @Autowired
    public CommentService(
            CommentRepository commentRepository,
            UserService userService,
            PostService postService,
            ConnectionService connectionService,
            @Lazy CommentLikeService commentLikeService,
            @Lazy ReplyCommentService replyCommentService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
        this.connectionService = connectionService;
        this.commentLikeService = commentLikeService;
        this.replyCommentService = replyCommentService;
    }

    public Page<CommentDto> getComments(Long postId, int page, int pageSize, String direction) {
        int currentPage = MyUtil.paginate(page, direction);
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        Page<CommentDto> result = this.commentRepository.getComments(postId, pageable);

        return result;
    }

    public CommentDto getLatestComment(Long postId) {
        Page<CommentDto> result = getComments(postId, -1, 1, "next");

        if (result.getContent().size() > 0) {
            addLikeStats(result.getContent());
            return result.getContent().get(0);
        }

        return null;
    }

    public CommentPaginationDto<CommentDto> getAllComments(Long postId, int page, int pageSize, String direction) {
        Page<CommentDto> result = getComments(postId, page, pageSize, direction);
        addLikeStats(result.getContent());

        return new CommentPaginationDto<CommentDto>(result.getContent(), result.getNumber(), pageSize,
                result.getTotalPages(),
                direction, result.getTotalElements());
    }

    private void addLikeStats(List<CommentDto> comments) {
        User currentUser = this.userService.getCurrentlyLoggedInUser();

        for (CommentDto comment : comments) {

            comment.setReplyCommentsExist(this.replyCommentService.replyCommentsExist(comment.getId()));
            comment.setLikesCount(this.commentLikeService.countCommentLikes(comment.getId()));
            comment.setCurrentUserHasLikedComment(
                    this.commentLikeService.alreadyLikedComment(comment.getId(), currentUser.getId()));
        }
    }

    private boolean canCreateComment(User user, Post post, Long ownerId) {

        return this.connectionService.isConnected(user.getId(), ownerId)
                || user.getRole() == Role.TEACHER;

    }

    private boolean enforceCommentLimit(User user, Post post) {
        long nowInSeconds = Instant.now().getEpochSecond();
        List<Comment> comments = this.commentRepository.findTop5ByUserIdAndPostIdOrderByIdDesc(user.getId(),
                post.getId());

        if (comments.size() < COMMENT_LIMIT) {
            return true;
        }
        long earliestCommentInSeconds = MyUtil.getSeconds(comments.get(comments.size() - 1).getCreatedAt());
        if (nowInSeconds - earliestCommentInSeconds > COMMENT_LIMIT_IN_SECONDS) {
            return true;
        }

        return false;
    }

    public void createComment(CreateCommentRequest request) {
        User user = this.userService.getUserById(request.getUserId());
        Post post = this.postService.getPostById(request.getPostId());

        if (!enforceCommentLimit(user, post)) {
            throw new BadRequestException("You have exceed the comment limit per 5 minutes");
        }

        if (!canCreateComment(user, post, request.getOwnerId())) {
            throw new ForbiddenException("You do not have neccessary permissions to post a comment");
        }

        this.commentRepository.save(new Comment(
                Jsoup.clean(request.getText(), Safelist.none()),
                false,
                user,
                post));
    }

    public Comment getCommentById(Long commentId) {
        return this.commentRepository.findById(commentId).orElseThrow(() -> {
            String error = String.format("Cannot find comment with the id %d", commentId);
            logger.info(error);
            throw new NotFoundException(error);
        });
    }

    private boolean canModifyComment(User user, Comment comment, Long ownerId) {
        return user.getId() == ownerId || user.getId() == comment.getUser().getId();
    }

    public void deleteComment(Long commentId, Long ownerId) {
        User user = this.userService.getCurrentlyLoggedInUser();
        Comment comment = getCommentById(commentId);

        if (!canModifyComment(user, comment, ownerId)) {
            throw new ForbiddenException("You do not have the necessary privileges to modify this comment");
        }

        this.commentRepository.delete(comment);
    }
}
