package com.hart.backend.parana.recentsearch;

import java.util.List;

import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.advice.BadRequestException;
import com.hart.backend.parana.advice.NotFoundException;
import com.hart.backend.parana.advice.ForbiddenException;
import com.hart.backend.parana.recentsearch.dto.RecentSearchDto;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecentSearchService {

    Logger logger = LoggerFactory.getLogger(RecentSearchService.class);

    private final RecentSearchRepository recentSearchRepository;

    private final UserService userService;

    @Autowired
    public RecentSearchService(
            RecentSearchRepository recentSearchRepository,
            UserService userService) {
        this.recentSearchRepository = recentSearchRepository;
        this.userService = userService;
    }

    private boolean alreadySearched(User user, String searchTerm) {
        return this.recentSearchRepository.alreadySearched(user.getId(), searchTerm);
    }

    public void createRecentSearch(User user, String searchTerm) {

        if (!alreadySearched(user, searchTerm)) {
            this.recentSearchRepository
                    .save(new RecentSearch(user, Jsoup.clean(searchTerm, Safelist.none())));
        }
    }

    public List<RecentSearchDto> getRecentSearches(Integer limit) {
        if (limit == null) {
            logger.info("Recent search limit was NOT specified");
            throw new BadRequestException("Search limit not specified");
        }

        User currentUser = this.userService.getCurrentlyLoggedInUser();

        return this.recentSearchRepository.getRecentSearches(currentUser.getId(), limit);
    }

    private RecentSearch getRecentSearchById(Long recentSearchId) {
        return this.recentSearchRepository.findById(recentSearchId).orElseThrow(() -> {
            String error = "Recent search was NOT found with the id " + recentSearchId;
            logger.info(error);
            throw new NotFoundException(error);
        });
    }

    private boolean canModifyRecentSearch(RecentSearch recentSearch) {
        User currentUser = this.userService.getCurrentlyLoggedInUser();
        return recentSearch.getUser().getId() == currentUser.getId();
    }

    public void deleteRecentSearch(Long recentSearchId) {
        RecentSearch recentSearch = getRecentSearchById(recentSearchId);
        if (!canModifyRecentSearch(recentSearch)) {
            throw new ForbiddenException("Cannot modify another user's search");
        }

        this.recentSearchRepository.delete(recentSearch);
    }
}
