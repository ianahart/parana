package com.hart.backend.parana.recommendation;

import com.hart.backend.parana.user.Role;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.util.MyUtil;

import java.util.List;

import com.hart.backend.parana.advice.BadRequestException;
import com.hart.backend.parana.advice.NotFoundException;
import com.hart.backend.parana.advice.ForbiddenException;
import com.hart.backend.parana.recommendation.dto.RecommendationDto;
import com.hart.backend.parana.recommendation.dto.RecommendationPaginationDto;
import com.hart.backend.parana.recommendation.request.CreateRecommendationRequest;

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

        if (author.getId() == teacher.getId()) {
            throw new BadRequestException("You cannot recommend yourself");
        }

        if (!canCreateRecommendation(author)) {
            throw new ForbiddenException("You have to be a teacher to recommend");
        }

        if (hasDuplicateRecommendation(author, teacher)) {
            throw new BadRequestException("You have already recommended " + teacher.getFirstName());
        }

        this.recommendationRepository.save(new Recommendation(
                Jsoup.clean(request.getRecommendation(), Safelist.none()),
                Jsoup.clean(request.getWords(), Safelist.none()),
                teacher,
                author));
    }

    private List<RecommendationDto> addDateToRecommendation(List<RecommendationDto> recommendations) {

        for (RecommendationDto recommendation : recommendations) {
            recommendation.setDate(MyUtil.createDate(recommendation.getCreatedAt()));
        }

        return recommendations;
    }

    public RecommendationPaginationDto<RecommendationDto> getRecommendations(Long teacherId, int page, int pageSize,
            String direction) {
        int currentPage = MyUtil.paginate(page, direction);
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        Page<RecommendationDto> result = this.recommendationRepository.getRecommendations(teacherId, pageable);
        List<RecommendationDto> recommendations = addDateToRecommendation(result.getContent());

        return new RecommendationPaginationDto<RecommendationDto>(
                recommendations,
                currentPage,
                pageSize,
                result.getTotalPages(),
                direction,
                result.getTotalElements());

    }

    public void deleteRecommendation(Long recommendationId) {
        Recommendation recommendation = getRecommendationById(recommendationId);
        User currentUser = this.userService.getCurrentlyLoggedInUser();

        if (recommendation.getAuthor().getId() != currentUser.getId()) {
            throw new ForbiddenException("Cannot delete another teacher's recommendation");
        }

        this.recommendationRepository.delete(recommendation);

    }
}
