package com.hart.backend.parana.favorite;

import com.hart.backend.parana.favorite.request.CreateFavoriteRequest;
import com.hart.backend.parana.favorite.response.CreateFavoriteResponse;
import com.hart.backend.parana.favorite.response.DeleteFavoriteResponse;
import com.hart.backend.parana.favorite.response.GetFavoriteResponse;

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

@RestController
@RequestMapping(path = "/api/v1/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<DeleteFavoriteResponse> deleteFavorite(@PathVariable("favoriteId") Long favoriteId) {
        this.favoriteService.deleteFavorite(favoriteId);
        return ResponseEntity.status(HttpStatus.OK).body(new DeleteFavoriteResponse("success"));
    }

    @GetMapping("/favorite")
    public ResponseEntity<GetFavoriteResponse> getFavorite(@RequestParam("userId") Long userId,
            @RequestParam("teacherId") Long teacherId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetFavoriteResponse("success", this.favoriteService.getFavorite(userId, teacherId)));
    }

    @PostMapping("")
    public ResponseEntity<CreateFavoriteResponse> createFavorite(@RequestBody CreateFavoriteRequest request) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CreateFavoriteResponse("success", this.favoriteService.createFavorite(request)));
    }
}
