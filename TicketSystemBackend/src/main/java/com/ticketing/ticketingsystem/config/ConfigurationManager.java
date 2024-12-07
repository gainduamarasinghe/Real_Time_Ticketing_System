package com.ticketing.ticketingsystem.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/config")
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class ConfigurationManager {

    private static final String CONFIG_JSON_FILE = "configuration.json";
    private final ObjectMapper objectMapper;

    public ConfigurationManager(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<String> saveConfigToJson(@Valid @RequestBody Configuration configuration) {
        try {
            // Write configuration to JSON file
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(CONFIG_JSON_FILE), configuration);
            return ResponseEntity.ok("Configuration saved as JSON successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error saving configuration to JSON: " + e.getMessage());
        }
    }
}
