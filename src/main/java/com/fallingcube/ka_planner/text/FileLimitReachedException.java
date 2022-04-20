package com.fallingcube.ka_planner.text;

public class FileLimitReachedException extends Throwable {
    public FileLimitReachedException() {
        System.err.println("Text file limit reached!");
    }
}
