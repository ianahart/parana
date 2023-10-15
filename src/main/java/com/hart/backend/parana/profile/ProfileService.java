package com.hart.backend.parana.profile;

import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.advice.NotFoundException;
import com.hart.backend.parana.amazon.AmazonService;

import java.util.Map;

import com.hart.backend.parana.advice.BadRequestException;
import com.hart.backend.parana.advice.ForbiddenException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfileService {
    private final int MAX_MEGA_BYTES = 2;
    private final String BUCKET_NAME = "arrow-date/parana/avatars";
    private final ProfileRepository profileRepository;
    private final UserService userService;
    private final AmazonService amazonService;

    @Autowired
    public ProfileService(
            ProfileRepository profileRepository,
            UserService userService,
            AmazonService amazonService) {
        this.profileRepository = profileRepository;
        this.userService = userService;
        this.amazonService = amazonService;
    }

    public Profile createProfile() {
        Profile profile = new Profile();
        return this.profileRepository.save(profile);
    }

    private String uploadProfilePhoto(MultipartFile file, Profile profile) {
        if (file.getSize() > MAX_MEGA_BYTES * 1024 * 1024) {
            throw new BadRequestException("File size exceeded 2 MB limit");
        }

        if (profile.getAvatarFilename() != null && !profile.getAvatarFilename().equals("")) {
            this.amazonService.delete(BUCKET_NAME, profile.getAvatarFilename());
        }

        String filename = this.amazonService.upload(BUCKET_NAME, file.getOriginalFilename(), file);
        Map<String, String> data = this.amazonService.getPublicUrl(BUCKET_NAME, filename);

        profile.setAvatarFilename(data.get("filename"));
        profile.setAvatarUrl(data.get("url"));
        this.profileRepository.save(profile);

        return profile.getAvatarUrl();
    }

    private String removeProfilePhoto(Profile profile) {
        this.amazonService.delete(BUCKET_NAME, profile.getAvatarFilename());
        profile.setAvatarFilename(null);
        profile.setAvatarUrl(null);

        this.profileRepository.save(profile);

        return profile.getAvatarUrl();
    }

    public Profile getProfileById(Long profileId) {
        return this.profileRepository
                .findById(profileId)
                .orElseThrow(() -> new NotFoundException("Profile not found"));
    }

    private boolean profileBelongsToCurrentUser(Profile profile) {
        User currentUser = this.userService.getCurrentlyLoggedInUser();
        return profile.getUser().getId() == currentUser.getId();
    }

    public String delegateProfilePhoto(MultipartFile file, String action, Long profileId) {
        Profile profile = getProfileById(profileId);
        if (!profileBelongsToCurrentUser(profile)) {
            throw new ForbiddenException("Cannot modify another user's profile");
        }
        if (action.equals("upload")) {
            return uploadProfilePhoto(file, profile);
        } else {
            return removeProfilePhoto(profile);
        }
    }

}
