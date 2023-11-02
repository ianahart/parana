package com.hart.backend.parana.replycomment.dto;

import java.util.List;

public class ReplyCommentPaginationDto<T> {
    private List<T> replyComments;
    private int page;
    private int pageSize;
    private int totalPages;
    private String direction;
    private long totalElements;

    public ReplyCommentPaginationDto() {

    }

    public ReplyCommentPaginationDto(
            List<T> replyComments,
            int page,
            int pageSize,
            int totalPages,
            String direction,
            long totalElements) {

        this.replyComments = replyComments;
        this.page = page;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.direction = direction;
        this.totalElements = totalElements;
    }

    public int getPage() {
        return page;
    }

    public List<T> getReplyComments() {
        return replyComments;
    }

    public String getDirection() {
        return direction;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setReplyComments(List<T> replyComments) {
        this.replyComments = replyComments;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}
