package com.hart.backend.parana.favorite;

import java.util.List;

import com.hart.backend.parana.advice.BadRequestException;
import com.hart.backend.parana.advice.NotFoundException;
import com.hart.backend.parana.connection.ConnectionService;
import com.hart.backend.parana.advice.ForbiddenException;
import com.hart.backend.parana.favorite.dto.FavoriteDto;
import com.hart.backend.parana.favorite.dto.FavoritePaginationDto;
import com.hart.backend.parana.favorite.dto.FullFavoriteDto;
import com.hart.backend.parana.favorite.request.CreateFavoriteRequest;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.util.MyUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FavoriteService {

    Logger logger = LoggerFactory.getLogger(FavoriteService.class);

    private final FavoriteRepository favoriteRepository;

    private final UserService userService;

    private final ConnectionService connectionService;

    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository, UserService userService,
            ConnectionService connectionService) {
        this.favoriteRepository = favoriteRepository;
        this.userService = userService;
        this.connectionService = connectionService;
    }

    private boolean validateFavorite(Long userId, Long teacherId) {

        return userId != null && teacherId != null;
    }

    private boolean alreadyFavoritedFavorite(Long userId, Long teacherId) {
        return this.favoriteRepository.alreadyFavoritedFavorite(userId, teacherId);
    }

    public FavoriteDto createFavorite(CreateFavoriteRequest request) {

        if (!validateFavorite(request.getUserId(), request.getTeacherId())) {
            String exception = "Missing userId and teacherId";
            logger.info(exception);
            throw new BadRequestException(exception);
        }

        if (alreadyFavoritedFavorite(request.getUserId(), request.getTeacherId())) {
            throw new BadRequestException("You already favorited this teacher");
        }

        User user = this.userService.getUserById(request.getUserId());
        User teacher = this.userService.getUserById(request.getTeacherId());

        Favorite favorite = new Favorite(true, user, teacher);
        this.favoriteRepository.save(favorite);

        return new FavoriteDto(favorite.getId(), favorite.getIsFavorited());
    }

    public FavoriteDto getFavorite(Long userId, Long teacherId) {
        if (!validateFavorite(userId, teacherId)) {
            String exception = "Missing userId and teacherId";
            logger.info(exception);
            throw new BadRequestException(exception);
        }

        return this.favoriteRepository.getFavorite(userId, teacherId);
    }

    private Favorite getFavoriteById(Long favoriteId) {
        return this.favoriteRepository.findById(favoriteId).orElseThrow(() -> {

            String error = String.format("Favorite with the id of %d was not found");
            logger.info(error);
            throw new NotFoundException(error);
        });
    }

    private boolean canModifyFavorite(Favorite favorite) {

        User currentUser = this.userService.getCurrentlyLoggedInUser();

        return favorite.getUser().getId() == currentUser.getId();
    }

    public void deleteFavorite(Long favoriteId) {
        Favorite favorite = getFavoriteById(favoriteId);

        if (!canModifyFavorite(favorite)) {
            throw new ForbiddenException("Can not unfavorite a favorite that does not belong to you");
        }

        this.favoriteRepository.delete(favorite);
    }

    private void addDateToFavorites(List<FullFavoriteDto> favorites) {
        for (FullFavoriteDto favorite : favorites) {

            favorite.setReadableDate(MyUtil.constructReadableDate(favorite.getCreatedAt()));

        }
    }

    private void addConnectedStatusToFavorites(Long userId, List<FullFavoriteDto> favorites) {

        for (FullFavoriteDto favorite : favorites) {

            boolean isConnected = this.connectionService.isConnected(userId, favorite.getTeacherId());
            favorite.setIsConnected(isConnected);
        }
    }

    public FavoritePaginationDto<FullFavoriteDto> getFavorites(Long userId, int page, int pageSize, String direction) {

        int currentPage = MyUtil.paginate(page, direction);
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());

        Page<FullFavoriteDto> result = this.favoriteRepository.getFavorites(userId, pageable);

        List<FullFavoriteDto> favorites = result.getContent();

        addDateToFavorites(favorites);
        addConnectedStatusToFavorites(userId, favorites);

        return new FavoritePaginationDto<FullFavoriteDto>(
                result.getContent(),
                currentPage,
                pageSize,
                result.getTotalPages(),
                direction,
                result.getTotalElements());
    }
}
