package com.fallingcube.ka_planner.text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.fallingcube.ka_planner.Main;
import com.fallingcube.ka_planner.constants.paths;
import com.fallingcube.ka_planner.json.JsonService;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

public class TextService {

    // Create new text - returns a text object
    public static Text createNewText(String name) throws StreamWriteException, DatabindException, IOException, FileLimitReachedException {
        int id = 0;

        int iteration = 0;
        boolean stop = false;
        while (iteration <= 10000 && stop == false) {
            if (!doesFileExist(paths.textFiles + iteration + ".json")) {
                stop = true;
                id = iteration;
            } else {
                iteration++;
            }
        }

        if (iteration >= 10000) {
            throw new FileLimitReachedException();
        }

        String dateTimeString = Main.getTimeAsFormatedString();

        Text text = new Text(id, name, "", dateTimeString, dateTimeString);
        JsonService.createJsonFileFromTextObject(text, paths.textFiles + id + ".json");

        return text;
    }

    public static Boolean doesFileExist(String fileName) {
        return new File(fileName).isFile();
    }
    
    // Read one or multiple lines from a file
    public static String readFile(String fileName) throws FileNotFoundException {
        String data = "";
        File myObj = new File(fileName);
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            data = myReader.nextLine();
        }
        myReader.close();
        return data;
    }

    // Create a file | Write one or multiple lines to a file
    public static void writeToFile(String fileName, String content) throws IOException {
        FileWriter myWriter = new FileWriter(fileName);
        myWriter.write(content);
        myWriter.close();
        System.out.println("Successfully wrote to the file.");
    }

    // deletes a file
    public static void deleteFile(String fileName) {
        new File(fileName).delete();
    }

    // deletes a text from the system
    public static void deleteText(String id) throws FileNotFoundException {
        if (!doesFileExist(paths.textFiles + id + ".json")) {
            throw new FileNotFoundException();
        }
        deleteFile(paths.textFiles + id + ".json");
        Text.editingArray[Integer.parseInt(id)] = null;
    }

    // returns the all text json file names (e.g.:"0;4;6;8;") 
    public static String getAllTextIds() {

        File folder = new File(paths.textFilesFolder);
        File[] listOfFiles = folder.listFiles();
        String fileNames = "";

        for (int i = 0; i < listOfFiles.length; i++) {
            fileNames += listOfFiles[i].getName().split(".json")[0] + ';';
        }

        return fileNames;
    }

    // gets the current editor of a text
    public static String getCurrentEditorByStringId(String StringId) {
        int id = Integer.parseInt(StringId);
        String editor = Text.editingArray[id];
        return editor;
    }

    // Set the current editor of a text (if allowed to) - returns true if allowed to, otherwise false
    public static Boolean setCurrentEditorByStringId(String StringId, String editor) {
        int id = Integer.parseInt(StringId);
        // if editor is same as new editor or null
        if (Text.editingArray[id] == editor || Text.editingArray[id] == null || Text.editingArray[id] == "") {
            Text.editingArray[id] = editor;
            return true;
        } else {
            return false;
        }
    }

    // removes the provided user (username String) from the editing array
    public static void removeUserFromEditingArray(String username) {
        for (int j = 0; j < Text.editingArray.length; j++) {
            if (Text.editingArray[j] != null) {
                if (Text.editingArray[j].toLowerCase().equals(username.toString().toLowerCase())) {
                    Text.editingArray[j] = "";
                 }   
            }
        }
    }

    // Overwrites an existing text object files
    public static void updateTextFile(String id, Text text) throws StreamWriteException, DatabindException, IOException {
        JsonService.createJsonFileFromTextObject(text, paths.textFiles + id + ".json");
    }

}
