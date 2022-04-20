package com.fallingcube.ka_planner.text;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.fallingcube.ka_planner.Main;
import com.fallingcube.ka_planner.constants.paths;
import com.fallingcube.ka_planner.json.JsonService;
import com.fallingcube.ka_planner.login.LoginService;
import com.fallingcube.ka_planner.token.InvalidTokenException;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "api/v2/text")
public class TextController {
    
    // Create new text
    // @PostMapping
    // public String createText() {
    //     System.out.println("test1");
    //     JsonService.createJsonFileFromTextObject(new Text(5, "Memories", "So cool", LocalDateTime.now().toString(), LocalDateTime.now().toString()), "ka_planner\\src\\main\\resources\\text\\5.json");
    //     return JsonService.textObjectToJsonStringSerializer(new Text(1, "test", "default", LocalDateTime.now().toString(), LocalDateTime.now().toString()));
    // }

    // Delete Text
    @DeleteMapping
    public void deleteText(@RequestParam String token, @RequestParam String id) {

        // ============ Validate provided token ============
        validateToken(token);
        // =================================================

        try {
            TextService.deleteText(id);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        
    }

    // Get text 
    @GetMapping
    public String getText(@RequestParam String token, @RequestParam String id) {

        // ============ Validate provided token ============
        validateToken(token);
        // =================================================

        try {
            return TextService.readFile(paths.textFiles + id + ".json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "editing")
    public String getCurrentEditorString(@RequestParam String token, @RequestParam String id) {

        // ============ Validate provided token ============
        validateToken(token);
        // =================================================
        
        return TextService.getCurrentEditorByStringId(id);
    }

    @PostMapping(path = "editing")
    public void setCurrentEditorString(@RequestParam String token, @RequestParam String id) {

        // ============ Validate provided token ============
        validateToken(token);
        // =================================================
        
        if (TextService.setCurrentEditorByStringId(id, token.split("@")[1])) {
            throw new ResponseStatusException(HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN); // 403
        }
    }


    @GetMapping(path = "allids")
    public String getAllIds(@RequestParam String token) {
        
        // ============ Validate provided token ============
        validateToken(token);
        // =================================================

        return TextService.getAllTextIds();

    }

    // MODIFY A TEXT
    // 
    // PUT REQUEST
    // Requires the users TOKEN and a TEXT (String JSON format)
    // 
    // Returns 403 if user is not allowed to edit
    // Returns 
    // 
    @PutMapping
    public void putText(@RequestParam String token, @RequestParam String content) {

        // ============ Validate provided token ============
        validateToken(token);
        // =================================================

        Text text; // submitted text
        Text oldText; // current text, server version
        try {
            text = JsonService.jsonStringToTextObject(content);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            oldText = JsonService.jsonStringToTextObject(TextService.readFile(paths.textFiles + String.valueOf(text.getId()) + ".json"));
        } catch (JsonProcessingException | FileNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String currentEditor = TextService.getCurrentEditorByStringId(String.valueOf(text.getId()));
        String requestEditor = token.split("@")[1].toLowerCase();

        if (currentEditor.equals(requestEditor) || new String(currentEditor).equals("")) {
            text.setDateOfCreation(oldText.getDateOfCreation());
            text.setEditingDate(Main.getTimeAsFormatedString());
            try {
                TextService.updateTextFile(String.valueOf(text.getId()), text);
            } catch (IOException e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    void validateToken(String token) {
        try {
            if (!LoginService.validateLoginWithToken(token)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        } catch (InvalidTokenException e1) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        } catch (IOException e2) {
            e2.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
