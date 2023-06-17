package com.plueone.server.jwtPayload.response;

import java.util.List;

public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String userId;
    private String name;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, String userId, String name, String email, List<String> roles) {
		this.token = accessToken;
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.roles = roles;
	} 

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}
