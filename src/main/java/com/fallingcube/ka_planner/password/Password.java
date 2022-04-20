package com.fallingcube.ka_planner.password;

public class Password {
    
    private String hash;
    private String salt;
    
    public Password(String hash, String salt) {
        this.hash = hash;
        this.salt = salt;
    }

    public String getSaltAsHexString() {
        return salt;
    }
    public byte[] getHexSaltAsByteArray() {
        return PasswordService.hexStringToByteArray(salt);
    }
    public String getHashAsHexString() {
        return hash;
    }
    public byte[] getHashAsByteArray() {
        return PasswordService.hexStringToByteArray(salt);
    }
    public void setHash(String hash) {
        this.hash = hash;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }

}
