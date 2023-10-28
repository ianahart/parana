package com.hart.backend.parana.favorite.request;

public class CreateFavoriteRequest {

    private Long userId;

    private Long teacherId;

    public CreateFavoriteRequest() {

    }

    public CreateFavoriteRequest(Long userId, Long teacherId) {
        this.userId = userId;
        this.teacherId = teacherId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}
