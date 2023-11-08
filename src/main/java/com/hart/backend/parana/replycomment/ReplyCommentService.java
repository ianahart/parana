package com.hart.backend.parana.replycomment;

import com.hart.backend.parana.comment.Comment;
import com.hart.backend.parana.comment.CommentService;
import com.hart.backend.parana.connection.ConnectionService;
import com.hart.backend.parana.privacy.PrivacyService;
import com.hart.backend.parana.replycomment.dto.ReplyCommentDto;
import com.hart.backend.parana.replycomment.dto.ReplyCommentPaginationDto;
import com.hart.backend.parana.replycomment.request.CreateReplyCommentRequest;
import com.hart.backend.parana.user.Role;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.util.MyUtil;
import com.hart.backend.parana.advice.NotFoundException;
import com.hart.backend.parana.advice.BadRequestException;
import com.hart.backend.parana.advice.ForbiddenException;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReplyCommentService {

    Logger logger = LoggerFactory.getLogger(ReplyCommentService.class);

    private final UserService userService;

    private final CommentService commentService;

    private final ConnectionService connectionService;

    private final ReplyCommentRepository replyCommentRepository;

    private final PrivacyService privacyService;

    @Autowired
    public ReplyCommentService(
            UserService userService,
            CommentService commentService,
            ReplyCommentRepository replyCommentRepository,
            ConnectionService connectionService,
            PrivacyService privacyService) {
        this.userService = userService;
        this.commentService = commentService;
        this.replyCommentRepository = replyCommentRepository;
        this.connectionService = connectionService;
        this.privacyService = privacyService;
    }

    public ReplyComment getReplyCommentById(Long replyCommentId) {
        return this.replyCommentRepository.findById(replyCommentId).orElseThrow(() -> {
            String error = String.format("Reply comment with the id %d was not found", replyCommentId);
            logger.info(error);
            throw new NotFoundException(error);
        });
    }

    private boolean canCreateReplyComment(User user, Long ownerId) {

        return this.connectionService.isConnected(user.getId(), ownerId) || user.getRole() == Role.TEACHER;
    }

    public void createReplyComment(CreateReplyCommentRequest request) {

        User user = this.userService.getUserById(request.getUserId());
        Comment comment = this.commentService.getCommentById(request.getCommentId());

        if (this.privacyService.blockedFromCreatingComments(request.getUserId(), request.getOwnerId())) {
            throw new ForbiddenException("You are blocked from replying to comments this teacher");
        }

        if (!canCreateReplyComment(user, request.getOwnerId())) {
            throw new ForbiddenException("You do not have necessary privileges to reply to a comment");
        }

        this.replyCommentRepository.save(
                new ReplyComment(user, comment, Jsoup.clean(request.getText(), Safelist.none())));
    }

    public boolean replyCommentsExist(Long commentId) {
        return this.replyCommentRepository.replyCommentsExist(commentId);
    }

    private Page<ReplyCommentDto> paginateReplyComments(Long commentId, int page, int pageSize, String direction) {
        int currentPage = MyUtil.paginate(page, direction);
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());

        return this.replyCommentRepository.getReplyComments(commentId, pageable);
    }

    public ReplyCommentPaginationDto<ReplyCommentDto> getReplyComments(Long commentId, int page, int pageSize,
            String direction) {
        Page<ReplyCommentDto> result = paginateReplyComments(commentId, page, pageSize, direction);

        return new ReplyCommentPaginationDto<ReplyCommentDto>(
                result.getContent(),
                result.getNumber(),
                pageSize,
                result.getTotalPages(),
                direction,
                result.getTotalElements());
    }

    private boolean canModifyReplyComment(User user, ReplyComment replyComment, Long ownerId) {
        return user.getId() == ownerId || user.getId() == replyComment.getUser().getId();
    }

    public void deleteReplyComment(Long replyCommentId, Long ownerId) {
        User user = this.userService.getCurrentlyLoggedInUser();
        ReplyComment replyComment = getReplyCommentById(replyCommentId);

        if (!canModifyReplyComment(user, replyComment, ownerId)) {
            throw new ForbiddenException("You do not have the necessary privileges to modify this comment");
        }

        this.replyCommentRepository.delete(replyComment);
    }

}
