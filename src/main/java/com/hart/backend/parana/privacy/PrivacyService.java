package com.hart.backend.parana.privacy;

import com.hart.backend.parana.privacy.dto.PrivacyDto;
import com.hart.backend.parana.privacy.dto.PrivacyPaginationDto;
import com.hart.backend.parana.privacy.request.CreatePrivacyRequest;
import com.hart.backend.parana.privacy.request.UpdatePrivacyRequest;

import java.util.List;

import com.hart.backend.parana.advice.NotFoundException;
import com.hart.backend.parana.user.User;
import com.hart.backend.parana.user.UserService;
import com.hart.backend.parana.util.MyUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private Page<PrivacyDto> paginatePrivacies(Long userId, int page, int pageSize, String direction) {
        int currentPage = MyUtil.paginate(page, direction);

        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        Page<PrivacyDto> result = this.privacyRepository.getPrivacies(userId, pageable);

        return result;
    }

    public PrivacyPaginationDto<PrivacyDto> getPrivacies(Long userId, int page, int pageSize, String direction) {

        Page<PrivacyDto> result = paginatePrivacies(userId, page, pageSize, direction);
        return new PrivacyPaginationDto<>(result.getContent(), result.getNumber(),
                pageSize,
                result.getTotalPages(),
                direction,
                result.getTotalElements());
    }

    private Privacy getPrivacyById(Long privacyId) {
        return this.privacyRepository.findById(privacyId)
                .orElseThrow(() -> new NotFoundException("Privacy with the id of " + privacyId + " was not found"));
    }

    public void updateBlockedPrivacy(UpdatePrivacyRequest request, Long privacyId) {
        Privacy privacy = getPrivacyById(privacyId);

        switch (request.getBlock()) {
            case "messages":
                privacy.setMessages(request.getIsChecked());
                break;
            case "posts":
                privacy.setPosts(request.getIsChecked());
                break;
            case "comments":
                privacy.setComments(request.getIsChecked());
                break;
        }

        if (!privacy.getPosts() && !privacy.getMessages() && !privacy.getComments()) {
            this.privacyRepository.delete(privacy);
        } else {
            this.privacyRepository.save(privacy);
        }

    }

    public boolean blockedFromCreatingPosts(Long authorId, Long ownerId) {
        return this.privacyRepository.blockedFromCreatingPosts(authorId, ownerId);

    }

    public boolean blockedFromCreatingComments(Long authorId, Long ownerId) {
        return this.privacyRepository.blockedFromCreatingComments(authorId, ownerId);

    }

    public List<Long> getBlockedUserPrivaciesMessages(User currentUser) {
        return currentUser.getBlockedUserPrivacies().stream()
                .filter(privacy -> privacy.getMessages())
                .map(privacy -> privacy.getBlockedByUser().getId()).toList();
    }

    public List<Long> getBlockedByUserPrivaciesMessages(User currentUser) {
        return currentUser.getBlockedByUserPrivacies().stream()
                .filter(privacy -> privacy.getMessages())
                .map(privacy -> privacy.getBlockedUser().getId()).toList();
    }

}
