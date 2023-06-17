package com.plueone.server.models;

import java.io.Serializable;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class MatchingScore {

    public String userId;
    public String name;
    public Double interestScore;
    public Double subInterestScore;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getInterestScore() {
        return interestScore;
    }

    public void setInterestScore(Double interestMatchingScore) {
        this.interestScore = interestMatchingScore;
    }

    public Double getSubInterestScore() {
        return subInterestScore;
    }

    public void setSubInterestScore(Double subInterestMatchingScore) {
        this.subInterestScore = subInterestMatchingScore;
    }

    public static JsonArray toJsonArr(List<MatchingScore> scores) {

        JsonArrayBuilder jArr = Json.createArrayBuilder();

        for (MatchingScore m : scores) {

            JsonObject json = Json.createObjectBuilder()
                    .add("userId", m.getUserId())
                    .add("name", m.getName())
                    .add("interestScore", m.getInterestScore())
                    .add("subInterestScore", m.getSubInterestScore())
                    .build();
            jArr.add(json);
        }
        return jArr.build();
    }

    @Override
    public String toString() {
        return "MatchingScore [interestScore=" + interestScore + ", name=" + name + ", subInterestScore="
                + subInterestScore + ", userId=" + userId + "]";
    }

    

}
