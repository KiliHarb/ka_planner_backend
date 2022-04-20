package com.fallingcube.ka_planner.user;

import com.fallingcube.ka_planner.password.PasswordService;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {

    private String token;
    private String username;
    private String hashedPassword;
    private String salt;
    private String lastLogin;

    public User() {
        super();
    }

    public User(String token, String username, String hashedPassword, String salt, String lastLogin) {
        this.setToken(token);
        this.setUsername(username);
        this.setPassword(hashedPassword);
        this.setSalt(salt);
        this.setLastLogin(lastLogin);
    }

    public String getSalt() {
        return salt;
    }

    @JsonIgnore
    public byte[] getSaltAsByteArray() {
        return PasswordService.hexStringToByteArray(salt);
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return hashedPassword;
    }

    @JsonIgnore
    public byte[] getPasswordAsByteArray() {
        return PasswordService.hexStringToByteArray(hashedPassword);
    }

    public void setPassword(String password) {
        this.hashedPassword = password;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
}
