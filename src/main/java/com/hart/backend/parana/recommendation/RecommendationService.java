package com.hart.backend.parana.recommendation;

import com.hart.backend.parana.user.Role;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.advice.BadRequestException;
import com.hart.backend.parana.advice.NotFoundException;
import com.hart.backend.parana.advice.ForbiddenException;
import com.hart.backend.parana.recommendation.request.CreateRecommendationRequest;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {

    Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    private final RecommendationRepository recommendationRepository;

    private final UserService userService;

    @Autowired
    public RecommendationService(
            RecommendationRepository recommendationRepository,
            UserService userService) {
        this.recommendationRepository = recommendationRepository;
        this.userService = userService;

    }

    public Recommendation getRecommendationById(Long recommendationId) {
        return this.recommendationRepository
                .findById(recommendationId)
                .orElseThrow(() -> {
                    String error = "Recommendation not found with the id " + recommendationId;
                    logger.info(error);
                    throw new NotFoundException(error);
                });
    }

    private boolean hasDuplicateRecommendation(User author, User teacher) {
        return this.recommendationRepository.hasDuplicateRecommendation(author.getId(), teacher.getId());
    }

    private boolean canCreateRecommendation(User author) {
        User currentUser = this.userService.getCurrentlyLoggedInUser();
        return author.getRole() == Role.TEACHER && author.getId() == currentUser.getId();
    }

    public void CreateRecommendation(CreateRecommendationRequest request) {
        User author = this.userService.getUserById(request.getAuthorId());
        User teacher = this.userService.getUserById(request.getTeacherId());

        if (!canCreateRecommendation(author)) {
            throw new ForbiddenException("You have to be a teacher to recommend");
        }

        if (hasDuplicateRecommendation(author, teacher)) {
            throw new BadRequestException("You have already recommended " + teacher.getFirstName());
        }
        // ing recommendation,
        // ing words,
        // r teacher,
        // r author) {

        this.recommendationRepository.save(new Recommendation(
                Jsoup.clean(request.getRecommendation(), Safelist.none()),
                Jsoup.clean(request.getWords(), Safelist.none()),
                teacher,
                author));

        System.out.println(request.getWords());

    }
}
