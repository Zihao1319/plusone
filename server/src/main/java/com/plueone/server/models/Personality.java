package com.plueone.server.models;

import java.io.Serializable;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Personality {

    private String userId;
    private Integer personalityId;
    private String personalityType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getPersonalityId() {
        return personalityId;
    }

    public void setPersonalityId(Integer personalityId) {
        this.personalityId = personalityId;
    }

    public String getPersonalityType() {
        return personalityType;
    }

    public void setPersonalityType(String personalityType) {
        this.personalityType = personalityType;
    }

    public static JsonArray toJsonArr(List<Personality> personalities) {

        JsonArrayBuilder jArr = Json.createArrayBuilder();

        for (Personality p : personalities) {

            JsonObject json = Json.createObjectBuilder()
                    .add("personalityId", p.getPersonalityId())
                    .add("personalityType", p.getPersonalityType())
                    .build();
            jArr.add(json);
        }
        return jArr.build();
    }

}
