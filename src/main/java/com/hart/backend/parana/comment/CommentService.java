package com.hart.backend.parana.comment;

import com.hart.backend.parana.comment.request.CreateCommentRequest;
import com.hart.backend.parana.connection.ConnectionService;

import java.time.Instant;
import java.util.List;

import com.hart.backend.parana.advice.ForbiddenException;
import com.hart.backend.parana.advice.BadRequestException;

import com.hart.backend.parana.post.Post;
import com.hart.backend.parana.post.PostService;
import com.hart.backend.parana.user.Role;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.util.MyUtil;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final int COMMENT_LIMIT = 5;
    private final int COMMENT_LIMIT_IN_SECONDS = 60 * 5;

    private final CommentRepository commentRepository;

    private final UserService userService;

    private final PostService postService;

    private final ConnectionService connectionService;

    @Autowired
    public CommentService(
            CommentRepository commentRepository,
            UserService userService,
            PostService postService,
            ConnectionService connectionService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
        this.connectionService = connectionService;
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
}
