package com.hart.backend.parana.privacy.response;

import com.hart.backend.parana.privacy.dto.PrivacyDto;
import com.hart.backend.parana.privacy.dto.PrivacyPaginationDto;

public class GetPrivacyResponse {

    private String message;

    private PrivacyPaginationDto<PrivacyDto> data;

    public GetPrivacyResponse() {

    }

    public GetPrivacyResponse(String message, PrivacyPaginationDto<PrivacyDto> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public PrivacyPaginationDto<PrivacyDto> getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(PrivacyPaginationDto<PrivacyDto> data) {
        this.data = data;
    }
}
