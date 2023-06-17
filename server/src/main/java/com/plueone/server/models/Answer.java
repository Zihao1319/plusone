package com.plueone.server.models;

import java.io.Serializable;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Answer {

    private Integer id;
    private String userId;
    private String promptId;
    private String promptQuestion;
    private String answer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPromptId() {
        return promptId;
    }

    public void setPromptId(String promptId) {
        this.promptId = promptId;
    }

    public String getPromptQuestion() {
        return promptQuestion;
    }

    public void setPromptQuestion(String promptQuestion) {
        this.promptQuestion = promptQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public static JsonArray toJsonArr(List<Answer> ans) {

        JsonArrayBuilder jArr = Json.createArrayBuilder();

        for (Answer a : ans) {

            JsonObject json = Json.createObjectBuilder()
                    .add("promptId", a.getPromptId())
                    .add("promptQuestion", a.getPromptQuestion())
                    .add("answer", a.getAnswer())
                    .build();
            jArr.add(json);
        }
        return jArr.build();
    }

}
