package com.fallingcube.ka_planner.session;

public class SessionService {

    public static boolean isUserActice(String username) {
        return Session.activeUsers.contains(new String(username));
    }

}
