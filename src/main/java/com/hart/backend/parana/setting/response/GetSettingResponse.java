package com.hart.backend.parana.setting.response;

import com.hart.backend.parana.setting.dto.SettingDto;

public class GetSettingResponse {

    private String message;

    private SettingDto data;

    public GetSettingResponse(String message, SettingDto data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public SettingDto getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(SettingDto data) {
        this.data = data;
    }
}
