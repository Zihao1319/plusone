package com.plueone.server.models;

import java.io.Serializable;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Interest {

    private String userId;
    private Integer interestId;
    private String interestName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getInterestId() {
        return interestId;
    }

    public void setInterestId(Integer interestId) {
        this.interestId = interestId;
    }

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    public static JsonArray toJsonArr(List<Interest> interests){

        JsonArrayBuilder jArr = Json.createArrayBuilder();
      
        for (Interest i : interests){

            JsonObject json = Json.createObjectBuilder()
                            .add("interestId", i.getInterestId())
                            .add("interestName", i.getInterestName())
                            .build();
            jArr.add(json);
        }
        return jArr.build();
    }

}
