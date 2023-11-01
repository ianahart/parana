package com.hart.backend.parana.comment;

import com.hart.backend.parana.comment.request.CreateCommentRequest;
import com.hart.backend.parana.comment.response.CreateCommentResponse;
import com.hart.backend.parana.comment.response.DeleteCommentResponse;
import com.hart.backend.parana.comment.response.GetCommentResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("")
    public ResponseEntity<GetCommentResponse> getComments(
            @RequestParam("postId") Long postId,
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("direction") String direction) {

        return ResponseEntity.status(HttpStatus.OK).body(new GetCommentResponse("success",
                this.commentService.getAllComments(postId, page, pageSize, direction)));
    }

    @PostMapping("")
    public ResponseEntity<CreateCommentResponse> createComment(@Valid @RequestBody CreateCommentRequest request) {
        this.commentService.createComment(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CreateCommentResponse("success"));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<DeleteCommentResponse> deleteComment(@PathVariable("commentId") Long commentId,
            @RequestParam("ownerId") Long ownerId) {
        this.commentService.deleteComment(commentId, ownerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new DeleteCommentResponse("success"));
    }
}
