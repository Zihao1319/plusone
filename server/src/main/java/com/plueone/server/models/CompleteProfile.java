package com.plueone.server.models;

import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class CompleteProfile {
    private String userId;
    private Profile profile;
    private Preference preference;
    private List<Language> languages;
    private List<Interest> interests;
    private List<Subinterest> subInterests;
    private List<Personality> personalities;
    private List<Image> images;
    private List<Answer> answers;
    private Double interestScore;
    private Double subInterestScore;

    public Double getInterestScore() {
        return interestScore;
    }

    public void setInterestScore(Double interestScore) {
        this.interestScore = interestScore;
    }

    public Double getSubInterestScore() {
        return subInterestScore;
    }

    public void setSubInterestScore(Double subInterestScore) {
        this.subInterestScore = subInterestScore;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public List<Interest> getInterests() {
        return interests;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    public List<Subinterest> getSubInterests() {
        return subInterests;
    }

    public void setSubInterests(List<Subinterest> subInterests) {
        this.subInterests = subInterests;
    }

    public List<Personality> getPersonalities() {
        return personalities;
    }

    public void setPersonalities(List<Personality> personalities) {
        this.personalities = personalities;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public static JsonObject toJson(CompleteProfile profile) {

        JsonObjectBuilder result = Json.createObjectBuilder();

        if (profile.getProfile() != null) {
            result.add("Profile", Profile.toJson(profile.getProfile()));
        } else {
            result.add("Profile", "");
        }

        // if (profile.getPreference() != null) {
        // result.add("Preference", Preference.toJson(profile.getPreference()));
        // } else {
        // result.add("Preference", "");
        // }

        if (profile.getLanguages() != null) {
            result.add("Language", Language.toJsonArr(profile.getLanguages()));
        } else {
            result.add("Language", "");
        }

        if (profile.getInterests() != null) {
            result.add("Interests", Interest.toJsonArr(profile.getInterests()));
        } else {
            result.add("Interests", "");
        }

        if (profile.getSubInterests() != null) {
            result.add("SubInterests", Subinterest.toJsonArr(profile.getSubInterests()));
        } else {
            result.add("SubInterests", "");
        }

        if (profile.getPersonalities() != null) {
            result.add("Personalities", Personality.toJsonArr(profile.getPersonalities()));
        } else {
            result.add("Personalities", "");
        }

        if (profile.getImages() != null) {
            result.add("Images", Image.toJsonArr(profile.getImages()));
        } else {
            result.add("Images", "");
        }

        if (profile.getAnswers() != null) {
            result.add("Answers", Answer.toJsonArr(profile.getAnswers()));
        } else {
            result.add("Answers", "");
        }

        if (profile.getInterestScore() != null) {
            result.add("InterestScore", profile.getInterestScore());
        } else {
            result.add("InterestScore", 0);
        }

        if (profile.getSubInterestScore() != null) {
            result.add("SubInterestScore", profile.getSubInterestScore());
        } else {
            result.add("SubInterestScore", 0);
        }

        return result.build();

    }

    public static JsonArray toJsonArray(List<JsonObject> cpJson) {
        JsonArrayBuilder jArr = Json.createArrayBuilder();

        for (JsonObject json : cpJson) {
            jArr.add(json);
        }
        return jArr.build();
    }

}
