package com.hart.backend.parana.connection.dto;

public class ConnectionStatusDto {

    private String status;
    private Boolean accepted;
    private Boolean pending;

    public ConnectionStatusDto() {

    }

    public ConnectionStatusDto(String status, Boolean accepted, Boolean pending) {
        this.status = status;
        this.accepted = accepted;
        this.pending = pending;
    }

    public String getStatus() {
        return status;
    }

    public Boolean getPending() {
        return pending;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPending(Boolean pending) {
        this.pending = pending;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
