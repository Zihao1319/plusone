package com.plueone.server.models;

import java.io.Serializable;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Prompt{

    private Integer promptId;
    private String promptQuestion;

    public Integer getPromptId() {
        return promptId;
    }

    public void setPromptId(Integer promptId) {
        this.promptId = promptId;
    }

    public String getPromptQuestion() {
        return promptQuestion;
    }

    public void setPromptQuestion(String question) {
        this.promptQuestion = question;
    }

    public static JsonArray toJsonArray(List<Prompt> prompts) {
        JsonArrayBuilder jArr = Json.createArrayBuilder();

        for (Prompt p : prompts) {

            JsonObject json = Json.createObjectBuilder()
                    .add("promptId", p.getPromptId())
                    .add("promptQuestion", p.getPromptQuestion())
                    .build();
            jArr.add(json);
        }
        return jArr.build();
    }

}
