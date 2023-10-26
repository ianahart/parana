package com.hart.backend.parana.review;

import com.hart.backend.parana.review.dto.PartialReviewDto;
import com.hart.backend.parana.review.dto.ReviewDto;
import com.hart.backend.parana.review.dto.ReviewPaginationDto;
import com.hart.backend.parana.review.request.CreateReviewRequest;
import com.hart.backend.parana.review.request.UpdateReviewRequest;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.util.MyUtil;
import com.hart.backend.parana.advice.BadRequestException;
import com.hart.backend.parana.advice.NotFoundException;
import com.hart.backend.parana.advice.ForbiddenException;
import com.hart.backend.parana.connection.ConnectionService;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    Logger logger = LoggerFactory.getLogger(ReviewService.class);
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final ConnectionService connectionService;

    @Autowired
    public ReviewService(
            ReviewRepository reviewRepository,
            UserService userService,
            ConnectionService connectionService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.connectionService = connectionService;
    }

    private boolean duplicateReview(Long userId, Long teacherId) {
        if (userId == null || teacherId == null) {
            logger.info("duplicateReview missing either userId or teacherId");
            throw new BadRequestException("Missing user and teacher paramters");
        }
        return this.reviewRepository.duplicateReview(userId, teacherId);
    }

    public void createReview(CreateReviewRequest request) {

        if (!this.connectionService.isConnected(request.getUserId(), request.getTeacherId())) {
            logger.info("User is not connected with this teacher");
            throw new BadRequestException("You are not connected yet with this teacher");
        }

        if (duplicateReview(request.getUserId(), request.getTeacherId())) {
            logger.info("User has already reviewed this review");
            throw new BadRequestException("You have already reviewed this teacher");
        }

        User user = this.userService.getUserById(request.getUserId());
        User teacher = this.userService.getUserById(request.getTeacherId());

        this.reviewRepository.save(new Review(
                request.getRating(),
                request.getReview(),
                user,
                teacher,
                false));
    }

    private Byte getAvgRating(Long teacherId) {
        return this.reviewRepository.getAvgRating(teacherId);
    }

    public ReviewPaginationDto<ReviewDto> getReviews(Long teacherId, int page, int pageSize, String direction) {
        int currentPage = MyUtil.paginate(page, direction);

        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        Page<ReviewDto> result = this.reviewRepository.getReviews(teacherId, pageable);

        return new ReviewPaginationDto<ReviewDto>(
                result.getContent(),
                currentPage,
                pageSize,
                result.getTotalPages(),
                direction,
                result.getTotalElements(),
                getAvgRating(teacherId));

    }

    public Review getReviewById(Long reviewId) {
        return this.reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("A review was not found"));
    }

    private boolean canModifyReview(Review review) {
        User user = this.userService.getCurrentlyLoggedInUser();
        boolean canModify = false;

        if (review.getUser().getId() == user.getId()) {
            canModify = true;
        }

        return canModify;
    }

    public void deleteReview(Long reviewId) {
        Review review = getReviewById(reviewId);
        if (!canModifyReview(review)) {
            throw new ForbiddenException("Cannot modify another user's review");
        }

        this.reviewRepository.delete(review);
    }

    public PartialReviewDto getPartialReview(Long reviewId) {
        Review review = getReviewById(reviewId);
        if (!canModifyReview(review)) {
            throw new ForbiddenException("Cannot modify another user's review");
        }

        return this.reviewRepository.getPartialReview(reviewId);
    }

    public void updateReview(Long reviewId, UpdateReviewRequest request) {
        Review review = getReviewById(reviewId);
        if (!canModifyReview(review)) {
            throw new ForbiddenException("Cannot modify another user's review");
        }

        review.setRating(request.getRating());
        review.setReview(Jsoup.clean(request.getReview(), Safelist.none()));

        this.reviewRepository.save(review);
    }
}
