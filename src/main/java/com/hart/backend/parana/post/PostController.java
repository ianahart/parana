package com.hart.backend.parana.post;

import com.hart.backend.parana.post.request.CreatePostRequest;
import com.hart.backend.parana.post.response.CreatePostResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping("")
    public ResponseEntity<CreatePostResponse> createPost(CreatePostRequest request) {
        this.postService.createPost(request);

        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new CreatePostResponse("success"));
    }
}
