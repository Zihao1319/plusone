package com.plueone.server.models;

import java.io.Serializable;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Profile {

    private String userId;
    private String name;
    private String gender;
    private Integer birthYear;
    private Integer age;
    private Integer horoscopeId;
    private String horoscopeName;
    private Integer height;
    private Integer weight;
    private Integer raceId;
    private String raceName;
    private String location;
    private String job;
    private String religion;
    private Integer educationId;
    private String educationName;
    private String maritalStatus;
    private String relationshipGoal;
    private String smoking;
    private String drinking;
    private String exercise;
    private Integer dietId;
    private String dietName;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getRelationshipGoal() {
        return relationshipGoal;
    }

    public void setRelationshipGoal(String relationshipGoal) {
        this.relationshipGoal = relationshipGoal;
    }

    public String getSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public String getDrinking() {
        return drinking;
    }

    public void setDrinking(String drinking) {
        this.drinking = drinking;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public Integer getHoroscopeId() {
        return horoscopeId;
    }

    public void setHoroscopeId(Integer horoscopeId) {
        this.horoscopeId = horoscopeId;
    }

    public String getHoroscopeName() {
        return horoscopeName;
    }

    public void setHoroscopeName(String horoscopeName) {
        this.horoscopeName = horoscopeName;
    }

    public Integer getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public Integer getEducationId() {
        return educationId;
    }

    public void setEducationId(Integer educationId) {
        this.educationId = educationId;
    }

    public String getEducationName() {
        return educationName;
    }

    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }

    public Integer getDietId() {
        return dietId;
    }

    public void setDietId(Integer dietId) {
        this.dietId = dietId;
    }

    public String getDietName() {
        return dietName;
    }

    public void setDietName(String dietName) {
        this.dietName = dietName;
    }


    public static Profile populate(SqlRowSet rs) {
        Profile p = new Profile();

        p.setUserId(rs.getString("user_id"));
        p.setName(rs.getString("name"));
        p.setGender(rs.getString("gender"));
        p.setBirthYear(rs.getInt("birth_year"));
        p.setAge(rs.getInt("age"));
        p.setHoroscopeId(rs.getInt("horoscope_id"));
        p.setHoroscopeName(rs.getString("horoscope_name"));
        p.setHeight(rs.getInt("height"));
        p.setWeight(rs.getInt("weight"));
        p.setRaceId(rs.getInt("race_id"));
        p.setRaceName(rs.getString("race_name"));
        p.setLocation(rs.getString("location"));
        p.setJob(rs.getString("job"));
        p.setReligion(rs.getString("religion"));
        p.setEducationId(rs.getInt("education_id"));
        p.setEducationName(rs.getString("education_name"));
        p.setMaritalStatus(rs.getString("marital_status"));
        p.setRelationshipGoal(rs.getString("relationship_goal"));
        p.setSmoking(rs.getString("smoking"));
        p.setDrinking(rs.getString("drinking"));
        p.setExercise(rs.getString("exercise"));
        p.setDietId(rs.getInt("diet_id"));
        p.setDietName(rs.getString("diet_name"));

        return p;
    }

    public static JsonObject toJson(Profile profile) {

        JsonObject json = Json.createObjectBuilder()
                .add("userId", profile.getUserId())
                .add("name", profile.getName())
                .add("gender", profile.getGender())
                .add("birthYear", profile.getBirthYear())
                .add("age", profile.getAge())
                .add("horoscopeId", profile.getHoroscopeId())
                .add("horoscopeName", profile.getHoroscopeName())
                .add("height", profile.getHeight())
                .add("weight", profile.getWeight())
                .add("raceId", profile.getRaceId())
                .add("raceName", profile.getRaceName())
                .add("location", profile.getLocation())
                .add("job", profile.getJob())
                .add("religion", profile.getReligion())
                .add("educationId", profile.getEducationId())
                .add("educationName", profile.getEducationName())
                .add("maritalStatus", profile.getMaritalStatus())
                .add("relationshipGoal", profile.getRelationshipGoal())
                .add("smoking", profile.getSmoking())
                .add("drinking", profile.getDrinking())
                .add("exercise", profile.getExercise())
                .add("dietId", profile.getDietId())
                .add("dietName", profile.getDietName())
                .add("interestScore", profile.getInterestScore() != null ? profile.getInterestScore() : 0)
                .add("subInterestScore", profile.getSubInterestScore() != null ? profile.getSubInterestScore() : 0)
                .build();

        return json;
    }

}
