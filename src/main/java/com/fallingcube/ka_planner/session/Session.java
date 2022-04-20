package com.fallingcube.ka_planner.session;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fallingcube.ka_planner.text.TextService;

public class Session extends SessionService {

    // If a user is part of this array the users token will be valid
    public static List<String> activeUsers = new ArrayList<>();
    
    private String username;

    public Session(String username) {
        this.setUsername(username);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void start() {
        Thread thread = new Thread() {
            public void run() {
                if (activeUsers.contains(new String(username))) {
                    System.err.println("There already is an active session of the user " + username + "!");
                    return;
                }
                try {
                    System.out.println("Started new session of user: " + username);
                    System.out.println("The user is going to be logged out at: " + LocalDateTime.now().plusWeeks(1).toString());
                    // Adds the user to the array
                    activeUsers.add(username);

                    // // One week
                    // for (int i = 0; i < 604800; i++) {
                    //     Thread.sleep(1000);
                    // }

                    // Test
                    for (int i = 0; i < 60 * 60 * 24; i++) {
                        Thread.sleep(1000);
                    }
                    // Test

                    System.out.println("End of session of user: " + username);

                    // Removes the user from the array
                    activeUsers.remove(username);

                    // Removes the user from the editing array
                    TextService.removeUserFromEditingArray(username);

                } catch(InterruptedException v) {
                    System.out.println(v);
                }
            }  
        };
        thread.start();
    }

}
