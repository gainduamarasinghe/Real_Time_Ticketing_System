package com.ticketing.ticketingsystem.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@RestController
@RequestMapping("/api/config")
public class ConfigurationManager {

    private static final String CONFIG_JSON_FILE = "configuration.json";

    // Method to save the configuration using JSON format with BufferedWriter
    @PostMapping
    public ResponseEntity<String> saveConfigToJson(@RequestBody Configuration configuration) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_JSON_FILE))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(configuration, writer);
            return ResponseEntity.ok("Configuration saved as JSON successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error saving configuration to JSON: " + e.getMessage());
        }
    }
}
