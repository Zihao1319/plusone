package com.plueone.server.models;

import java.io.Serializable;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Subinterest {

    private String userId;
    private Integer subInterestId;
    private Integer interestId;
    private String subInterestName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getSubInterestId() {
        return subInterestId;
    }

    public void setSubInterestId(Integer subInterestId) {
        this.subInterestId = subInterestId;
    }

    public Integer getInterestId() {
        return interestId;
    }

    public void setInterestId(Integer interestId) {
        this.interestId = interestId;
    }

    public String getSubInterestName() {
        return subInterestName;
    }

    public void setSubInterestName(String subInterestName) {
        this.subInterestName = subInterestName;
    }

    public static JsonArray toJsonArr(List<Subinterest> subs){

        JsonArrayBuilder jArr = Json.createArrayBuilder();
      
        for (Subinterest s : subs){

            JsonObject json = Json.createObjectBuilder()
                            .add("subInterestId", s.getSubInterestId())
                            .add("subInterestName", s.getSubInterestName())
                            .add("interestId", s.getInterestId())
                            .build();
            jArr.add(json);
        }
        return jArr.build();
    }

}
