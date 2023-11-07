package com.hart.backend.parana.privacy.request;

import com.hart.backend.parana.privacy.dto.BlockDto;

public class CreatePrivacyRequest {

    private Long blockedByUserId;

    private Long blockedUserId;

    private BlockDto block;

    public CreatePrivacyRequest() {

    }

    public CreatePrivacyRequest(Long blockedByUserId, Long blockedUserId, BlockDto block) {
        this.blockedUserId = blockedByUserId;
        this.blockedUserId = blockedUserId;
        this.block = block;
    }

    public Long getBlockedByUserId() {
        return blockedByUserId;
    }

    public Long getBlockedUserId() {
        return blockedUserId;
    }

    public BlockDto getBlock() {
        return block;
    }

    public void setBlock(BlockDto block) {
        this.block = block;
    }

    public void setBlockedUserId(Long blockedUserId) {
        this.blockedUserId = blockedUserId;
    }

    public void setBlockedByUserId(Long blockedByUserId) {
        this.blockedByUserId = blockedByUserId;
    }
}
