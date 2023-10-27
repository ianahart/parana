package com.hart.backend.parana.recentsearch.response;

import java.util.List;

import com.hart.backend.parana.recentsearch.dto.RecentSearchDto;

public class GetRecentSearchResponse {

    private String message;
    private List<RecentSearchDto> data;

    public GetRecentSearchResponse() {

    }

    public GetRecentSearchResponse(String message, List<RecentSearchDto> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public List<RecentSearchDto> getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(List<RecentSearchDto> data) {
        this.data = data;
    }
}
