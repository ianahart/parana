package com.hart.backend.parana.recommendation;

import com.hart.backend.parana.recommendation.request.CreateRecommendationRequest;
import com.hart.backend.parana.recommendation.response.CreateRecommendationResponse;
import com.hart.backend.parana.recommendation.response.DeleteRecommendationResponse;
import com.hart.backend.parana.recommendation.response.GetRecommendationResponse;

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
@RequestMapping(path = "/api/v1/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("")
    public ResponseEntity<GetRecommendationResponse> getRecommendations(
            @RequestParam("teacherId") Long teacherId,
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("direction") String direction) {

        return ResponseEntity.status(HttpStatus.OK).body(new GetRecommendationResponse("success",
                this.recommendationService.getRecommendations(teacherId, page, pageSize, direction)));
    }

    @PostMapping("")
    public ResponseEntity<CreateRecommendationResponse> createRecommendation(
            @Valid @RequestBody CreateRecommendationRequest request) {
        this.recommendationService.CreateRecommendation(request);
        return ResponseEntity
                .status(HttpStatus.CREATED).body(new CreateRecommendationResponse("success"));
    }

    @DeleteMapping("/{recommendationId}")
    public ResponseEntity<DeleteRecommendationResponse> deleteRecommendation(
            @PathVariable("recommendationId") Long recommendationId) {

        this.recommendationService.deleteRecommendation(recommendationId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new DeleteRecommendationResponse("success"));
    }
}
