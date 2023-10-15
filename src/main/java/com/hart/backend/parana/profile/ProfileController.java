package com.hart.backend.parana.profile;

import com.hart.backend.parana.profile.request.ProfilePhotoRequest;
import com.hart.backend.parana.profile.response.ProfilePhotoResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
