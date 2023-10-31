package com.hart.backend.parana.postlike;

import com.hart.backend.parana.postlike.request.CreatePostLikeRequest;
import com.hart.backend.parana.postlike.response.CreatePostLikeResponse;
import com.hart.backend.parana.postlike.response.DeletePostLikeResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "/api/v1/posts/{postId}/likes")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @Autowired
    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<DeletePostLikeResponse> deletePostLike(@PathVariable("postId") Long postId,
            @PathVariable("userId") Long userId) {
        this.postLikeService.deletePostLike(postId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(new DeletePostLikeResponse("success"));
    }

    @PostMapping("")
    public ResponseEntity<CreatePostLikeResponse> createPostLike(@PathVariable("postId") Long postId,
            @RequestBody CreatePostLikeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreatePostLikeResponse("success", this.postLikeService.createPostLike(request)));
    }

}
