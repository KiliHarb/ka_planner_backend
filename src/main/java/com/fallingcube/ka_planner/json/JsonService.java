package com.fallingcube.ka_planner.json;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import com.fallingcube.ka_planner.constants.paths;
import com.fallingcube.ka_planner.text.Text;
import com.fallingcube.ka_planner.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonService {

    private static ObjectMapper objectMapper = new ObjectMapper();

    // serializes a text object into a JSON String
    public static String textObjectToJsonStringSerializer(Text text) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(text);
        return json;
    }

    // serializes a text object into a JSON String and writes it into an output file
    public static void createJsonFileFromTextObject(Text text, String outputFile) throws StreamWriteException, DatabindException, IOException {
        objectMapper.writeValue(new File(outputFile), text);
    }

    // serializes a user object into a JSON String and writes it into an output file
    public static void createJsonFileFromUserObject(User user, String outputFile) throws StreamWriteException, DatabindException, IOException {
        objectMapper.writeValue(new File(outputFile), user);
    }

    // Reads the JSON String out of a file and converts it into a User object
    public static User JsonFileToUserObject(String jsonFile) throws StreamReadException, DatabindException, IOException {
        User user = objectMapper.readValue(Paths.get(paths.userFiles + jsonFile).toFile(), User.class);
        return user;
    }

    // converts the provided string to a text object
    public static Text jsonStringToTextObject(String jsonString) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Text text = mapper.readValue(jsonString, Text.class);
        return text;
    }
}
