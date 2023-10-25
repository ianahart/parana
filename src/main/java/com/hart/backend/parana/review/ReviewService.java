package com.hart.backend.parana.review;

import com.hart.backend.parana.review.request.CreateReviewRequest;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.advice.BadRequestException;
import com.hart.backend.parana.connection.ConnectionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
                teacher));
    }
}
