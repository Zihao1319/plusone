package com.plueone.server.models;

import java.io.Serializable;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Horoscope {

    private Integer horoscopeId;
    private String name;

    public Integer getHoroscopeId() {
        return horoscopeId;
    }

    public void setHoroscopeId(Integer horoscopeId) {
        this.horoscopeId = horoscopeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static JsonArray toJsonArray(List<Horoscope> horos) {
        JsonArrayBuilder jArr = Json.createArrayBuilder();

        for (Horoscope h : horos) {

            JsonObject json = Json.createObjectBuilder()
                    .add("horoscopeId", h.getHoroscopeId())
                    .add("horoscopeName", h.getName())
                    .build();
            jArr.add(json);
        }
        return jArr.build();
    }

}
