package com.plueone.server.models;

import java.io.Serializable;

public class Frienship {

    private Integer friendshipId;
    private String requestorId;
    private String requesteeId;
    private String status;

    public Integer getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(Integer friendshipId) {
        this.friendshipId = friendshipId;
    }

    public String getRequestorId() {
        return requestorId;
    }

    public void setRequestorId(String requestorId) {
        this.requestorId = requestorId;
    }

    public String getRequesteeId() {
        return requesteeId;
    }

    public void setRequesteeId(String requesteeId) {
        this.requesteeId = requesteeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
