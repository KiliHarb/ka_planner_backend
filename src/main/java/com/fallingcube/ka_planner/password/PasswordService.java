package com.fallingcube.ka_planner.password;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.fallingcube.ka_planner.user.User;
import com.fallingcube.ka_planner.user.UserService;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

public class PasswordService {

    // Generates salt
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    // Generates hash from password and salt
    public static byte[] generateHash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return hash;
    }

    // Generates a Password object out of a generated salt and password String
    public static Password generatePassword(String passwordString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Password password = new Password(null, null);
        byte[] salt = generateSalt();
        byte[] hash = generateHash(passwordString, salt);
        password.setSalt(byteArrayToHexString(salt));
        password.setHash(byteArrayToHexString(hash));
        return password;
    }

    // byte[] to hex String
    public static String byteArrayToHexString(byte[] bytes) {
        char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    // hex String to byte[]
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static Boolean validatePassword(String username, String passwordStringB) throws StreamReadException, DatabindException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        User user = UserService.getUser(username);
        byte[] salt = user.getSaltAsByteArray();
        byte[] hashB = generateHash(passwordStringB, salt);
        byte[] hashA = user.getPasswordAsByteArray();
        if (new String(hashA).equals(new String(hashB))) {
            return true;
        } else {
            return false;
        }
    }

}
