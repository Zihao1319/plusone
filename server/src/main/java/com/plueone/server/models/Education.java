package com.plueone.server.models;

import java.io.Serializable;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Education {

    private Integer educationId;
    private String name;

    public Integer getEducationId() {
        return educationId;
    }

    public void setEducationId(Integer educationId) {
        this.educationId = educationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static JsonArray toJsonArray(List<Education> educations) {
        JsonArrayBuilder jArr = Json.createArrayBuilder();

        for (Education e : educations) {

            JsonObject json = Json.createObjectBuilder()
                    .add("educationId", e.getEducationId())
                    .add("educationName", e.getName())
                    .build();
            jArr.add(json);
        }
        return jArr.build();
    }

}
