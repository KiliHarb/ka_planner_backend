package com.fallingcube.ka_planner.login;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.fallingcube.ka_planner.password.PasswordService;
import com.fallingcube.ka_planner.session.Session;
import com.fallingcube.ka_planner.session.SessionService;
import com.fallingcube.ka_planner.text.TextService;
import com.fallingcube.ka_planner.token.InvalidTokenException;
import com.fallingcube.ka_planner.token.TokenService;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

public class LoginService {

    public static Boolean validateLogin(String username, String password) throws StreamReadException, DatabindException, NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        return PasswordService.validatePassword(username, password);
    }

    public static String loginValid(String username) throws NoSuchAlgorithmException, StreamReadException, DatabindException, IOException {
        
        TextService.removeUserFromEditingArray(username);

        String newToken = TokenService.generateToken(); 

        if (SessionService.isUserActice(username)) {
            TokenService.overwriteToken(username, newToken);
        } else {
            TokenService.overwriteToken(username, newToken);
            Session session = new Session(username);
            session.start();
        }

        return newToken + "@" + username;
    }

    // Validates the login with a token username combination (token@user)
    public static boolean validateLoginWithToken(String tokenUsername) throws InvalidTokenException, StreamReadException, DatabindException, IOException {

        if (tokenUsername.equals("") || !tokenUsername.contains("@")) {
            throw new InvalidTokenException();
        }

        String token = tokenUsername.split("@")[0];
        String username = tokenUsername.split("@")[1];
        if (TokenService.tokenValidator(username, token)) {
            if (SessionService.isUserActice(username)) {
                TextService.removeUserFromEditingArray(username);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
