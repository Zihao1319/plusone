package com.plueone.server.models.JwtAuth;

public class RegistrationDTO {
    private String name;
    private String email;
    private String password;

    public RegistrationDTO() {
        super();
    }

    public RegistrationDTO(String name, String email, String password) {
        super();
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RegistrationDTO [name=" + name + ", password=" + password + "]";
    }

}
