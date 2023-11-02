package com.hart.backend.parana.replycomment;

import com.hart.backend.parana.comment.Comment;
import com.hart.backend.parana.comment.CommentService;
import com.hart.backend.parana.connection.ConnectionService;
import com.hart.backend.parana.replycomment.request.CreateReplyCommentRequest;
import com.hart.backend.parana.user.Role;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;

import com.hart.backend.parana.advice.NotFoundException;
import com.hart.backend.parana.advice.BadRequestException;
import com.hart.backend.parana.advice.ForbiddenException;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyCommentService {

    Logger logger = LoggerFactory.getLogger(ReplyCommentService.class);

    private final UserService userService;

    private final CommentService commentService;

    private final ConnectionService connectionService;

    private final ReplyCommentRepository replyCommentRepository;

    @Autowired
    public ReplyCommentService(
            UserService userService,
            CommentService commentService,
            ReplyCommentRepository replyCommentRepository,
            ConnectionService connectionService) {
        this.userService = userService;
        this.commentService = commentService;
        this.replyCommentRepository = replyCommentRepository;
        this.connectionService = connectionService;
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

        if (!canCreateReplyComment(user, request.getOwnerId())) {
            throw new ForbiddenException("You do not have necessary privileges to reply to a comment");
        }

        this.replyCommentRepository.save(
                new ReplyComment(user, comment, Jsoup.clean(request.getText(), Safelist.none())));
    }
}
