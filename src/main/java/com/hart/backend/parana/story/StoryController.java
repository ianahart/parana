package com.hart.backend.parana.story;

import com.hart.backend.parana.story.request.CreateStoryRequest;
import com.hart.backend.parana.story.response.CreateStoryResponse;
import com.hart.backend.parana.story.response.GetStoryResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stories")
public class StoryController {

    private final StoryService storyService;

    @Autowired
    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @PostMapping("")
    public ResponseEntity<CreateStoryResponse> createStory(CreateStoryRequest request) {
        this.storyService.createPhotoStory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateStoryResponse("success"));
    }

}
