package com.hart.backend.parana.privacy;

import com.hart.backend.parana.privacy.request.CreatePrivacyRequest;
import com.hart.backend.parana.privacy.response.CreatePrivacyResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/privacies")
public class PrivacyController {

    private final PrivacyService privacyService;

    @Autowired
    public PrivacyController(PrivacyService privacyService) {
        this.privacyService = privacyService;
    }

    @PostMapping("")
    public ResponseEntity<CreatePrivacyResponse> createOrUpdatePrivacy(@RequestBody CreatePrivacyRequest request) {
        this.privacyService.createOrUpdatePrivacy(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CreatePrivacyResponse("success"));
    }
}
