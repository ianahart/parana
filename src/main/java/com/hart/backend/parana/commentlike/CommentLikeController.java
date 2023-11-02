package com.hart.backend.parana.commentlike;

import com.hart.backend.parana.commentlike.request.CreateCommentLikeRequest;
import com.hart.backend.parana.commentlike.response.CreateCommentLikeResponse;
import com.hart.backend.parana.commentlike.response.DeleteCommentLikeResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comments/{commentId}/likes")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @Autowired
    public CommentLikeController(CommentLikeService commentLikeService) {
        this.commentLikeService = commentLikeService;
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<DeleteCommentLikeResponse> deleteCommentLike(@PathVariable("commentId") Long commentId, @PathVariable("userId") Long userId) {
      this.commentLikeService.deleteCommentLike(commentId, userId);
      return ResponseEntity
        .status(HttpStatus.OK)
        .body(new DeleteCommentLikeResponse("success"));
    }

    @PostMapping("")
    public ResponseEntity<CreateCommentLikeResponse> createCommentLike(@PathVariable("commentId") Long commentId,
            @RequestBody CreateCommentLikeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CreateCommentLikeResponse("success",
                        this.commentLikeService.createCommentLike(commentId, request.getUserId())));
    }
}
