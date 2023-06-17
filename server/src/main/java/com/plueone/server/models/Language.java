package com.plueone.server.models;

import java.io.Serializable;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Language {

    private String userId;
    private Integer languageId;
    private String languageName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public static JsonArray toJsonArr(List<Language> langs) {

        JsonArrayBuilder jArr = Json.createArrayBuilder();

        for (Language l : langs) {

            JsonObject json = Json.createObjectBuilder()
                    .add("languageId", l.getLanguageId())
                    .add("languageName", l.getLanguageName())
                    .build();
            jArr.add(json);
        }
        return jArr.build();
    }

}
