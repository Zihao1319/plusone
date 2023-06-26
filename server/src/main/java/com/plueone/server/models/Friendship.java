package com.plueone.server.models;

import java.io.Serializable;
import java.util.List;

import com.plueone.server.service.MatchingService;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Friendship {

    private Integer friendshipId;
    private String requestorId;
    private String requesteeId;
    private Double interestScore;
    private Double subInterestScore;
    private String status;

    public Double getInterestScore() {
        return interestScore;
    }

    public void setInterestScore(Double interestScore) {
        this.interestScore = interestScore;
    }

    public Double getSubInterestScore() {
        return subInterestScore;
    }

    public void setSubInterestScore(Double subInterestScore) {
        this.subInterestScore = subInterestScore;
    }

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

    public static JsonArray toJsonArray(List<Friendship> friends) {
        JsonArrayBuilder jArr = Json.createArrayBuilder();

        for (Friendship f : friends) {

            JsonObject json = Json.createObjectBuilder()
                    .add("requestorId", f.getRequestorId())
                    .add("requesteeId", f.getRequesteeId())
                    .add("interestScore", f.getInterestScore())
                    .add("subInterestScore", f.getSubInterestScore())
                    .add("status", f.getStatus())
                    .build();

            jArr.add(json);
        }
        return jArr.build();
    }

}
