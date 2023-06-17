package com.plueone.server.models.JwtAuth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class User {

    private String userId;
    private String name;
    private String email;
    private String phoneNum;
    private String password;
    private List<Role> roles;
    // private Preference preference;
    // private List<Interest> interests;
    // private List<Subinterest> subInterests;
    // private Personality personality;
    // private List<Language> languages;
    // private List<Answer> profileAnswers;
    // private List<Image> images;

    public String getUserId() {
        return userId;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {
    }

    public User(String username, String email, String password) {
        this.name = username;
        this.email = email;
        this.password = password;
    }

    // public List<Image> getImages() {
    // return images;
    // }

    // public void setImages(List<Image> images) {
    // this.images = images;
    // }

    // public Preference getPreference() {
    // return preference;
    // }

    // public void setPreference(Preference preference) {
    // this.preference = preference;
    // }

    // public List<Interest> getInterests() {
    // return interests;
    // }

    // public void setInterests(List<Interest> interests) {
    // this.interests = interests;
    // }

    // public List<Subinterest> getSubInterests() {
    // return subInterests;
    // }

    // public void setSubInterests(List<Subinterest> subInterests) {
    // this.subInterests = subInterests;
    // }

    // public Personality getPersonality() {
    // return personality;
    // }

    // public void setPersonality(Personality personality) {
    // this.personality = personality;
    // }

    // public List<Language> getLanguages() {
    // return languages;
    // }

    // public void setLanguages(List<Language> languages) {
    // this.languages = languages;
    // }

    // public List<Answer> getProfileAnswers() {
    // return profileAnswers;
    // }

    // public void setProfileAnswers(List<Answer> profileAnswers) {
    // this.profileAnswers = profileAnswers;
    // }

    public static User populate(SqlRowSet rs) {
        User u = new User();

        u.setName(rs.getString("name"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setUserId(rs.getString("user_id"));

        List<Role> roles = new ArrayList<>();

        do {
            Role role = new Role();
            role.setId(rs.getInt("role_id"));
            role.setRoleName(rs.getString("role_name"));
            roles.add(role);

        } while (rs.next());

        u.setRoles(roles);

        return u;

    }

}
