package com.hart.backend.parana.recentsearch.response;

public class DeleteRecentSearchResponse {

    private String message;

    public DeleteRecentSearchResponse() {

    }

    public DeleteRecentSearchResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
