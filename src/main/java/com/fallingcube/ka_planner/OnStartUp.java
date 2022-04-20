package com.fallingcube.ka_planner;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.fallingcube.ka_planner.user.UserService;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class OnStartUp {
    
    @EventListener(ApplicationReadyEvent.class)
    public void startUp() {
        // try {
        //     try {
        //         UserService.createUser("lumind01", "lumind18");
        //         UserService.createUser("lumind02", "lumind18");
        //         UserService.createUser("Kilian", "Kilian2212");
        //     } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
        //         e.printStackTrace();
        //     }
        //  } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }

}
