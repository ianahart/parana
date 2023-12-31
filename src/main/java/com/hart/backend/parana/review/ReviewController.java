package com.hart.backend.parana.review;

import com.hart.backend.parana.review.request.CreateReviewRequest;
import com.hart.backend.parana.review.request.UpdateReviewRequest;
import com.hart.backend.parana.review.response.CreateReviewResponse;
import com.hart.backend.parana.review.response.DeleteReviewResponse;
import com.hart.backend.parana.review.response.GetPartialReviewResponse;
import com.hart.backend.parana.review.response.GetReviewResponse;
import com.hart.backend.parana.review.response.GetReviewStatsResponse;
import com.hart.backend.parana.review.response.UpdateReviewResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/stats")
    public ResponseEntity<GetReviewStatsResponse> getReviewStats(
            @RequestParam("topReviewSize") int topReviewSize,
            @RequestParam("reviewSize") int reviewSize) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GetReviewStatsResponse("success",
                        this.reviewService.getReviewStats(topReviewSize, reviewSize)));
    }

    @PostMapping("")
    public ResponseEntity<CreateReviewResponse> createReview(@Valid @RequestBody CreateReviewRequest request) {
        this.reviewService.createReview(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CreateReviewResponse("success"));
    }

    @GetMapping("")
    public ResponseEntity<GetReviewResponse> getReviews(
            @RequestParam("teacherId") Long teacherId,
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("direction") String direction) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new GetReviewResponse("success", this.reviewService.getReviews(teacherId, page, pageSize, direction)));

    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<DeleteReviewResponse> deleteReview(@PathVariable("reviewId") Long reviewId) {
        this.reviewService.deleteReview(reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(new DeleteReviewResponse("success"));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<GetPartialReviewResponse> getPartialReview(@PathVariable("reviewId") Long reviewId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetPartialReviewResponse("success", this.reviewService.getPartialReview(reviewId)));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<UpdateReviewResponse> updateReview(@PathVariable("reviewId") Long reviewId,
            @Valid @RequestBody UpdateReviewRequest request) {
        this.reviewService.updateReview(reviewId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UpdateReviewResponse("success"));
    }
}
