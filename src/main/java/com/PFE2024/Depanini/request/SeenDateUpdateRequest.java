package com.PFE2024.Depanini.request;

import java.util.List;

public class SeenDateUpdateRequest {
    private List<Long> messageIds;
    private Long userId;

    // Getters and setters
    public List<Long> getMessageIds() {
        return messageIds;
    }

    public void setMessageIds(List<Long> messageIds) {
        this.messageIds = messageIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
