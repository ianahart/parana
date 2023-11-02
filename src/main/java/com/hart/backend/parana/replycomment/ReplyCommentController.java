package com.hart.backend.parana.replycomment;

import com.hart.backend.parana.replycomment.request.CreateReplyCommentRequest;
import com.hart.backend.parana.replycomment.response.CreateReplyCommentResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/reply-comments")
public class ReplyCommentController {

    private final ReplyCommentService replyCommentService;

    @Autowired
    public ReplyCommentController(ReplyCommentService replyCommentService) {
        this.replyCommentService = replyCommentService;
    }

    @PostMapping("")
    public ResponseEntity<CreateReplyCommentResponse> createReplyComment(
            @Valid @RequestBody CreateReplyCommentRequest request) {
        this.replyCommentService.createReplyComment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateReplyCommentResponse("success"));
    }

}
