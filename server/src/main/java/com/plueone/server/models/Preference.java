package com.plueone.server.models;

import java.io.Serializable;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Preference {

    private Integer preferenceId;
    private String userId;
    private String genderPref;
    private Integer dietPrefId;
    private String dietPreference;
    private Integer racePrefId;
    private String racePreference;
    private Integer minHeight;
    private Integer maxHeight;
    private Integer minAge;
    private Integer maxAge;

    public Integer getDietPrefId() {
        return dietPrefId;
    }

    public void setDietPrefId(Integer dietPrefId) {
        this.dietPrefId = dietPrefId;
    }

    public Integer getRacePrefId() {
        return racePrefId;
    }

    public void setRacePrefId(Integer racePrefId) {
        this.racePrefId = racePrefId;
    }

    public Integer getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(Integer preferenceId) {
        this.preferenceId = preferenceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGenderPref() {
        return genderPref;
    }

    public void setGenderPref(String gender) {
        this.genderPref = gender;
    }

    public String getDietPreference() {
        return dietPreference;
    }

    public void setDietPreference(String dietPreference) {
        this.dietPreference = dietPreference;
    }

    public String getRacePreference() {
        return racePreference;
    }

    public void setRacePreference(String racePreference) {
        this.racePreference = racePreference;
    }

    public Integer getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(Integer minHeight) {
        this.minHeight = minHeight;
    }

    public Integer getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Integer maxHeight) {
        this.maxHeight = maxHeight;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public static Preference populate(SqlRowSet rs) {

        final Preference pref = new Preference();
        pref.setDietPrefId(rs.getInt("diet_pref_id"));
        pref.setDietPreference(rs.getString("diet_preference"));
        pref.setGenderPref(rs.getString("gender_pref"));
        pref.setMaxAge(rs.getInt("maxAge"));
        pref.setMinAge(rs.getInt("minAge"));
        pref.setMaxHeight(rs.getInt("maxHeight"));
        pref.setMinHeight(rs.getInt("minHeight"));
        pref.setRacePrefId(rs.getInt("race_pref_id"));
        pref.setRacePreference(rs.getString("race_preference"));
        pref.setUserId(rs.getString("user_id"));
        pref.setPreferenceId(rs.getInt("preference_id"));

        return pref;
    }

    public static JsonObject toJson(Preference preference) {

        JsonObject json = Json.createObjectBuilder()
                .add("preferenceId", preference.getPreferenceId())
                .add("userId", preference.getUserId())
                .add("genderPref", preference.getGenderPref())
                .add("dietPrefId", preference.getDietPrefId())
                .add("dietPreference", preference.getDietPreference())
                .add("racePrefId", preference.getRacePrefId())
                .add("racePreference", preference.getRacePreference())
                .add("minHeight", preference.getMinHeight())
                .add("maxHeight", preference.getMaxHeight())
                .add("minAge", preference.getMinAge())
                .add("maxAge", preference.getMaxAge())
                .build();

        return json;
    }

    @Override
    public String toString() {
        return "Preference [dietPrefId=" + dietPrefId + ", dietPreference=" + dietPreference + ", genderPref="
                + genderPref + ", maxAge=" + maxAge + ", maxHeight=" + maxHeight + ", minAge=" + minAge + ", minHeight="
                + minHeight + ", preferenceId=" + preferenceId + ", racePrefId=" + racePrefId + ", racePreference="
                + racePreference + ", userId=" + userId + "]";
    }

}
