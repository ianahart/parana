package com.hart.backend.parana.profile;

import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.advice.NotFoundException;
import com.hart.backend.parana.amazon.AmazonService;
import com.hart.backend.parana.geocode.GeocodeService;
import com.hart.backend.parana.profile.dto.TeacherProfileDto;
import com.hart.backend.parana.profile.dto.UserProfileDto;
import com.hart.backend.parana.profile.request.UpdateProfileRequest;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

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
    private final GeocodeService geocodeService;

    @Autowired
    public ProfileService(
            ProfileRepository profileRepository,
            UserService userService,
            AmazonService amazonService,
            GeocodeService geocodeService) {
        this.profileRepository = profileRepository;
        this.userService = userService;
        this.amazonService = amazonService;
        this.geocodeService = geocodeService;
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

    public void updateTeacher(UpdateProfileRequest request, Profile profile) {

        String city = Jsoup.clean(request.getCity(), Safelist.none());
        String state = Jsoup.clean(request.getState(), Safelist.none());
        Map<String, Double> coords = this.geocodeService.geocode(city, state);

        profile.setBio(Jsoup.clean(request.getBio(), Safelist.none()));
        profile.setAboutLesson(Jsoup.clean(request.getAboutLesson(), Safelist.none()));
        profile.setCity(city);
        profile.setFirstLessonFree(request.getFirstLessonFree());
        profile.setHomeMountain(Jsoup.clean(request.getHomeMountain(), Safelist.none()));
        profile.setPerHour(Jsoup.clean(request.getPerHour(), Safelist.none()));
        profile.setStance(request.getStance());
        profile.setState(state);
        profile.setTags(Jsoup.clean(request.getTags(), Safelist.none()));
        profile.setTerrain(Jsoup.clean(request.getTerrain(), Safelist.none()));
        profile.setYearsSnowboarding(request.getYearsSnowboarding());
        profile.setTravelUpTo(Jsoup.clean(request.getTravelUpTo(), Safelist.none()));
        profile.setLatitude(coords.get("latitude"));
        profile.setLongitude(coords.get("longitude"));

        this.profileRepository.save(profile);
    }

    public void updateUser(UpdateProfileRequest request, Profile profile) {

        profile.setBio(Jsoup.clean(request.getBio(), Safelist.none()));
        profile.setYearsSnowboarding(request.getYearsSnowboarding());
        profile.setCity(Jsoup.clean(request.getCity(), Safelist.none()));
        profile.setState(Jsoup.clean(request.getState(), Safelist.none()));
        profile.setStance(Jsoup.clean(request.getStance(), Safelist.none()));
        profile.setHomeMountain(Jsoup.clean(request.getHomeMountain(), Safelist.none()));
        profile.setTravelUpTo(Jsoup.clean(request.getTravelUpTo(), Safelist.none()));
        profile.setTerrain(Jsoup.clean(request.getTerrain(), Safelist.none()));

        this.profileRepository.save(profile);
    }

    private boolean profileBelongsTo(Long profileId) {
        return this.userService.getCurrentlyLoggedInUser().getProfile().getId() == profileId;
    }

    public void updateProfile(UpdateProfileRequest request, Long profileId) {
        if (!profileBelongsTo(profileId)) {
            throw new ForbiddenException("Cannot update another user's profile");
        }
        Profile profile = getProfileById(profileId);
        if (request.getFormType().equalsIgnoreCase("TEACHER")) {
            updateTeacher(request, profile);
        } else {
            updateUser(request, profile);
        }
    }

    private boolean isProfileNew(Timestamp timestamp) {
        long createdAtInSeconds = (timestamp.getTime() / 1000L);
        long nowInSeconds = Instant.now().getEpochSecond();
        final int oneMonth = 2592000;
        return nowInSeconds - createdAtInSeconds < oneMonth;

    }

    public TeacherProfileDto retrieveTeacher(Long profileId) {
        TeacherProfileDto teacherProfile = this.profileRepository.retrieveTeacher(profileId);
        teacherProfile.setIsNewTeacher(isProfileNew(teacherProfile.getCreatedAt()));
        return teacherProfile;
    }

    public UserProfileDto retrieveUser(Long profileId) {
        return this.profileRepository.retrieveUser(profileId);
    }
}
