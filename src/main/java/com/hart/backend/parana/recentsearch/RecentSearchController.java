package com.hart.backend.parana.recentsearch;

import com.hart.backend.parana.recentsearch.response.DeleteRecentSearchResponse;
import com.hart.backend.parana.recentsearch.response.GetRecentSearchResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/recent-searches")
public class RecentSearchController {

    private final RecentSearchService recentSearchService;

    @Autowired
    public RecentSearchController(RecentSearchService recentSearchService) {
        this.recentSearchService = recentSearchService;
    }

    @GetMapping("")
    public ResponseEntity<GetRecentSearchResponse> getRecentSearches(@RequestParam("limit") Integer limit) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetRecentSearchResponse("success", this.recentSearchService.getRecentSearches(limit)));
    }

    @DeleteMapping("/{recentSearchId}")
    public ResponseEntity<DeleteRecentSearchResponse> deleteRecentSearch(@PathVariable("recentSearchId") Long recentSearchId) {
        this.recentSearchService.deleteRecentSearch(recentSearchId);

        return ResponseEntity
        .status(HttpStatus.OK)
        .body(new DeleteRecentSearchResponse("success"));
    }
}
