package com.hart.backend.parana.user;

import com.hart.backend.parana.recentsearch.RecentSearchService;
import com.hart.backend.parana.user.dto.TeacherDto;
import com.hart.backend.parana.user.dto.UserDto;
import com.hart.backend.parana.user.dto.UserPaginationDto;
import com.hart.backend.parana.user.request.UpdateUserEmailRequest;
import com.hart.backend.parana.user.request.UpdateUserPasswordRequest;
import com.hart.backend.parana.user.response.DeleteUserResponse;
import com.hart.backend.parana.user.response.GetNonBlockedUserResponse;
import com.hart.backend.parana.user.response.GetUserSuggestionResponse;
import com.hart.backend.parana.user.response.GetUsersResponse;
import com.hart.backend.parana.user.response.SearchTeacherResponse;
import com.hart.backend.parana.user.response.SyncUserResponse;
import com.hart.backend.parana.user.response.UpdateUserEmailResponse;
import com.hart.backend.parana.user.response.UpdateUserPasswordResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserService userService;
    private final RecentSearchService recentSearchService;

    @Autowired
    public UserController(UserService userService, RecentSearchService recentSearchService) {
        this.userService = userService;
        this.recentSearchService = recentSearchService;
    }

    @GetMapping("/non-blocked")
    public ResponseEntity<GetNonBlockedUserResponse> getNonBlockedUsers(
            @RequestParam("userId") Long userId,
            @RequestParam("blockType") String blockType,
            @RequestParam("fullName") String fullName,
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("direction") String direction) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GetNonBlockedUserResponse("success",
                        this.userService.getNonBlockedUsers(userId, blockType, fullName, page, pageSize, direction)));

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<DeleteUserResponse> deletUser(@PathVariable("userId") Long userId) {
        this.userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new DeleteUserResponse("success"));
    }

    @PatchMapping("/{userId}/change-email")
    public ResponseEntity<UpdateUserEmailResponse> updateEmail(@PathVariable("userId") Long userId,
            @RequestBody UpdateUserEmailRequest request) {
        this.userService.updateEmail(userId, request.getEmail(), request.getPassword());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new UpdateUserEmailResponse("success"));
    }

    @PatchMapping("/{userId}/change-password")
    public ResponseEntity<UpdateUserPasswordResponse> updatePassword(@PathVariable("userId") Long userId,
            @RequestBody UpdateUserPasswordRequest request) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new UpdateUserPasswordResponse("success", this.userService.updatePassword(userId, request)));
    }

    @GetMapping("/suggestions")
    public ResponseEntity<GetUserSuggestionResponse> getUserSugggestions() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GetUserSuggestionResponse("success", this.userService.getUserSuggestions()));
    }

    @GetMapping("/sync")
    public ResponseEntity<SyncUserResponse> syncUser(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SyncUserResponse("success", this.userService.syncUser(request)));
    }

    @GetMapping("/search")
    public ResponseEntity<SearchTeacherResponse> searchTeachers(
            @RequestParam("searchTerm") String searchTerm,
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("direction") String direction) {

        User currentUser = this.userService.getCurrentlyLoggedInUser();

        if (searchTerm.trim().length() > 0) {
            this.recentSearchService.createRecentSearch(currentUser, searchTerm);
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new SearchTeacherResponse("success",
                        this.userService.searchTeachers(searchTerm, page, pageSize, direction)));

    }

    @GetMapping("")
    public ResponseEntity<GetUsersResponse<?>> getUsers(
            @RequestParam("role") String role,
            @RequestParam("page") int page,
            @RequestParam("direction") String direction,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("rate") int rate,
            @RequestParam("distance") Double distance) {

        switch (role) {
            case "TEACHER":
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new GetUsersResponse<UserPaginationDto<TeacherDto>>("success",
                                this.userService.retrieveTeachers(page, pageSize, direction, rate, distance)));

            case "USER":
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new GetUsersResponse<UserPaginationDto<UserDto>>("success",
                                this.userService.retrieveUsers(page, pageSize, direction)));

            default:
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new GetUsersResponse<UserPaginationDto<TeacherDto>>("success",
                                this.userService.retrieveTeachers(page, pageSize, direction, rate, distance)));

        }

    }
}
