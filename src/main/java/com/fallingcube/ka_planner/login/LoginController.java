package com.fallingcube.ka_planner.login;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.fallingcube.ka_planner.token.InvalidTokenException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping(path = "api/v2/login")
public class LoginController {
    
    @PostMapping(value = "/login")
    public String login(@RequestParam String username,  @RequestParam String password) {
        try {
            if (LoginService.validateLogin(username.toLowerCase(), password) == true) {
                return LoginService.loginValid(username.toLowerCase());
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/token")
    public void tokenCheckUp(@RequestParam String token) {
        try {
            if (LoginService.validateLoginWithToken(token)) {
                throw new ResponseStatusException(HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

}
