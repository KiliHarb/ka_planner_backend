package com.fallingcube.ka_planner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Main extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Don't forget to set 'Access-Control-Allow-Origin'!");
    }

    public static String getTimeAsString() {
        return String.valueOf(new Date().getTime());
    }

    public static String getTimeAsFormatedString() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")).toString();
    }
}
