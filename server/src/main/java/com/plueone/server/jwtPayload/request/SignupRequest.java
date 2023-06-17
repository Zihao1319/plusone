package com.plueone.server.jwtPayload.request;

import java.util.List;

import com.plueone.server.models.JwtAuth.Role;

public class SignupRequest {

    private String name;
    private String email;
    private List<Role> roles;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "SignupRequest [email=" + email + ", name=" + name + ", password=" + password + ", roles=" + roles + "]";
    }

    

}
