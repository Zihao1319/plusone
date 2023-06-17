package com.plueone.server.models;

import java.io.Serializable;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Race {

    private Integer raceId;
    private String name;

    public Integer getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    public String getRaceName() {
        return name;
    }

    public void setRaceName(String raceName) {
        this.name = raceName;
    }

    public static JsonArray toJsonArray(List<Race> races) {
        JsonArrayBuilder jArr = Json.createArrayBuilder();

        for (Race r : races) {

            JsonObject json = Json.createObjectBuilder()
                    .add("raceId", r.getRaceId())
                    .add("raceName", r.getRaceName())
                    .build();
            jArr.add(json);
        }
        return jArr.build();
    }

}
