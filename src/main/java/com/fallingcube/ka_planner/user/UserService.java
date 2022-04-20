package com.fallingcube.ka_planner.user;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.fallingcube.ka_planner.constants.paths;
import com.fallingcube.ka_planner.json.JsonService;
import com.fallingcube.ka_planner.password.Password;
import com.fallingcube.ka_planner.password.PasswordService;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

public class UserService {

    // Creates a new user JSON file from a user object
    public static void createUser(String name, String passwordString) throws NoSuchAlgorithmException, InvalidKeySpecException, StreamWriteException, DatabindException, IOException {
        Password password = PasswordService.generatePassword(passwordString);
        JsonService.createJsonFileFromUserObject(new User(null, name, password.getHashAsHexString(), password.getSaltAsHexString(), ""), paths.userFiles + name + ".json");
    }

    // Overwrites an existing user
    public static void overwriteUser(String name, User user) throws StreamWriteException, DatabindException, IOException {
        JsonService.createJsonFileFromUserObject(user, paths.userFiles + name + ".json");
    }

    // Get User by name
    public static User getUser(String name) throws StreamReadException, DatabindException, IOException {
        return JsonService.JsonFileToUserObject(name + ".json");
    }

    public static void overwriteLastLogin(String name, String lastLogin) throws StreamReadException, DatabindException, IOException {
        User user = getUser(name);
        user.setLastLogin(lastLogin);
        overwriteUser(name, user);
    }

}