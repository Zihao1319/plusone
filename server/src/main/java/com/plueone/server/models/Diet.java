package com.plueone.server.models;

import java.io.Serializable;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Diet {

    private Integer dietId;
    private String name;

    public Integer getDietId() {
        return dietId;
    }

    public void setDietId(Integer dietId) {
        this.dietId = dietId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static JsonArray toJsonArray(List<Diet> diets){
        JsonArrayBuilder jArr = Json.createArrayBuilder();
      
        for (Diet d : diets){

            JsonObject json = Json.createObjectBuilder()
                            .add("dietId", d.getDietId())
                            .add("dietName", d.getName())
                            .build();
            jArr.add(json);
        }
        return jArr.build();
    }

}
