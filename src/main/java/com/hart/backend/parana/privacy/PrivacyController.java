package com.hart.backend.parana.privacy;

import com.hart.backend.parana.privacy.request.CreatePrivacyRequest;
import com.hart.backend.parana.privacy.request.UpdatePrivacyRequest;
import com.hart.backend.parana.privacy.response.CreatePrivacyResponse;
import com.hart.backend.parana.privacy.response.GetPrivacyResponse;
import com.hart.backend.parana.privacy.response.UpdatePrivacyResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/privacies")
public class PrivacyController {

    private final PrivacyService privacyService;

    @Autowired
    public PrivacyController(PrivacyService privacyService) {
        this.privacyService = privacyService;
    }

    @GetMapping("")
    public ResponseEntity<GetPrivacyResponse> getPrivacies(
            @RequestParam("userId") Long userId,
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("direction") String direction) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GetPrivacyResponse("success",
                        this.privacyService.getPrivacies(userId, page, pageSize, direction)));
    }

    @PostMapping("")
    public ResponseEntity<CreatePrivacyResponse> createOrUpdatePrivacy(@RequestBody CreatePrivacyRequest request) {
        this.privacyService.createOrUpdatePrivacy(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CreatePrivacyResponse("success"));
    }

    @PatchMapping("/{privacyId}")
    public ResponseEntity<UpdatePrivacyResponse> updateBlockedPrivacy(@RequestBody UpdatePrivacyRequest request,
            @PathVariable("privacyId") Long privacyId) {
        this.privacyService.updateBlockedPrivacy(request, privacyId);
        return ResponseEntity.status(HttpStatus.OK).body(new UpdatePrivacyResponse("success"));
    }
}
