package com.hart.backend.parana.commentlike;

import com.hart.backend.parana.comment.Comment;
import com.hart.backend.parana.comment.CommentService;
import com.hart.backend.parana.commentlike.dto.CreateCommentLikeDto;
import com.hart.backend.parana.advice.BadRequestException;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeService {

    private final UserService userService;

    private final CommentService commentService;

    private final CommentLikeRepository commentLikeRepository;

    @Autowired
    public CommentLikeService(
            UserService userService,
            CommentService commentService,
            CommentLikeRepository commentLikeRepository) {
        this.userService = userService;
        this.commentService = commentService;
        this.commentLikeRepository = commentLikeRepository;
    }

    public boolean alreadyLikedComment(Long commentId, Long userId) {
        return this.commentLikeRepository.alreadyLikedComment(commentId, userId);
    }

    public CreateCommentLikeDto createCommentLike(Long commentId, Long userId) {

        if (alreadyLikedComment(commentId, userId)) {
            throw new BadRequestException("You have already liked this comment");
        }

        User user = this.userService.getUserById(userId);
        Comment comment = this.commentService.getCommentById(commentId);

        this.commentLikeRepository.save(new CommentLike(user, comment));

        return new CreateCommentLikeDto(true);
    }

    public long countCommentLikes(Long commentId) {
        return this.commentLikeRepository.countByCommentId(commentId);
    }

    public void deleteCommentLike(Long commentId, Long userId) {
        User currentUser = this.userService.getCurrentlyLoggedInUser();
        if (currentUser.getId() == userId) {
            CommentLike commentLike = this.commentLikeRepository.findCommentLikeByCommentIdAndUserId(commentId, userId);
            this.commentLikeRepository.delete(commentLike);

        }

    }
}
