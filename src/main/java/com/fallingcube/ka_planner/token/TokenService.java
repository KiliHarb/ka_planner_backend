package com.fallingcube.ka_planner.token;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.fallingcube.ka_planner.password.PasswordService;
import com.fallingcube.ka_planner.user.User;
import com.fallingcube.ka_planner.user.UserService;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

public class TokenService {
    
    // Generates a random String, 32 Characters
    public static String generateToken() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] values = new byte[32];
        secureRandom.nextBytes(values);
        String randomString = PasswordService.byteArrayToHexString(values);
        return randomString;
    }

    // Compares the token in the JSON file an the token which was send by the client
    public static Boolean tokenValidator(String username, String tokenB) throws StreamReadException, DatabindException, IOException {
        User user = UserService.getUser(username);
        String tokenA = user.getToken();
        if (new String(tokenA).equals(new String(tokenB))) {
            return true;
        } else {
            return false;
        }
    }

    // Overwrites the token of an exsiting user
    public static void overwriteToken(String username, String newToken) throws StreamReadException, DatabindException, IOException {
        User user = UserService.getUser(username);
        if (user == null) {
            return;
        }
        user.setToken(newToken);
        UserService.overwriteUser(username, user);
    }

}
