package com.hart.backend.parana.connection.request;

public class GetConnectionRequest {

    private Long userId;
    private String role;

    public GetConnectionRequest() {

    }

    public GetConnectionRequest(Long userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
