package com.hart.backend.parana.profile;

import com.hart.backend.parana.profile.dto.TeacherProfileDto;
import com.hart.backend.parana.profile.dto.UserProfileDto;
import com.hart.backend.parana.profile.request.ProfilePhotoRequest;
import com.hart.backend.parana.profile.request.UpdateProfileRequest;
import com.hart.backend.parana.profile.response.GetProfileResponse;
import com.hart.backend.parana.profile.response.ProfilePhotoResponse;
import com.hart.backend.parana.profile.response.UpdateProfileResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/profiles")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PatchMapping("/{profileId}/file-upload")
    public ResponseEntity<ProfilePhotoResponse> delegateProfilePhoto(ProfilePhotoRequest request,
            @PathVariable("profileId") Long profileId) {
        return ResponseEntity.status(HttpStatus.OK).body(new ProfilePhotoResponse("success",
                this.profileService.delegateProfilePhoto(request.getFile(), request.getAction(), profileId)));
    }

    @PatchMapping("/{profileId}")
    public ResponseEntity<UpdateProfileResponse> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request,
            @PathVariable("profileId") Long profileId) {
        this.profileService.updateProfile(request, profileId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new UpdateProfileResponse("success"));
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<GetProfileResponse<?>> getProfile(@PathVariable("profileId") Long profileId,
            @RequestParam("role") String role) {

        if (role.equalsIgnoreCase("TEACHER")) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new GetProfileResponse<TeacherProfileDto>("success",
                            this.profileService.retrieveTeacher(profileId)));

        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetProfileResponse<UserProfileDto>("success",
                        this.profileService.retrieveUser(profileId)));

    }
}
