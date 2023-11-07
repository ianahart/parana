package com.hart.backend.parana.privacy;

import com.hart.backend.parana.privacy.request.CreatePrivacyRequest;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivacyService {

    private final PrivacyRepository privacyRepository;

    private final UserService userService;

    @Autowired
    public PrivacyService(PrivacyRepository privacyRepository, UserService userService) {
        this.privacyRepository = privacyRepository;
        this.userService = userService;
    }

    private void createPrivacy(CreatePrivacyRequest request) {
        User blockedByUser = this.userService.getUserById(request.getBlockedByUserId());
        User blockedUser = this.userService.getUserById(request.getBlockedUserId());

        Privacy privacy = new Privacy(
                request.getBlock().getMessages(),
                request.getBlock().getPosts(),
                request.getBlock().getComments(),
                blockedUser,
                blockedByUser);

        this.privacyRepository.save(privacy);

    }

    private void updatePrivacy(CreatePrivacyRequest request, Privacy privacy) {

        if (request.getBlock().getComments()) {
            privacy.setComments(request.getBlock().getComments());

        }

        if (request.getBlock().getPosts()) {
            privacy.setPosts(request.getBlock().getPosts());

        }

        if (request.getBlock().getMessages()) {
            privacy.setMessages(request.getBlock().getMessages());

        }

        this.privacyRepository.save(privacy);
    }

    public void createOrUpdatePrivacy(CreatePrivacyRequest request) {

        Privacy existingPrivacy = this.privacyRepository
                .getExistingPrivacy(request.getBlockedUserId(),
                        request.getBlockedByUserId());

        if (existingPrivacy != null) {
            updatePrivacy(request, existingPrivacy);
        } else {
            createPrivacy(request);
        }
    }
}
