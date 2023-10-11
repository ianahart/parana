package com.hart.backend.parana.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(
            ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile createProfile() {
        Profile profile = new Profile();
        return this.profileRepository.save(profile);
    }

}
