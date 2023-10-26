package com.hart.backend.parana.recommendation;

import com.hart.backend.parana.recommendation.request.CreateRecommendationRequest;
import com.hart.backend.parana.recommendation.response.CreateRecommendationResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }


    @PostMapping("")
    public ResponseEntity<CreateRecommendationResponse> createRecommendation(@Valid @RequestBody CreateRecommendationRequest request) {
        this.recommendationService.CreateRecommendation(request);
        return ResponseEntity
        .status(HttpStatus.CREATED).
        body(new CreateRecommendationResponse("success"));
    }
}
