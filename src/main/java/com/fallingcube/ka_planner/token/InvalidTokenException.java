package com.fallingcube.ka_planner.token;

public class InvalidTokenException extends Throwable {

    public InvalidTokenException() {
        System.err.println("The provided token is invalid!");
    }
    
}
