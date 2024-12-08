package com.ticketing.ticketingsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketing.ticketingsystem.config.Configuration;
import com.ticketing.ticketingsystem.service.ConfigurationService;
import com.ticketing.ticketingsystem.validation.InputValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/config")
@CrossOrigin(origins = "http://localhost:4200")
public class ConfigurationController {
    private final ConfigurationService configurationService;
    private final ObjectMapper objectMapper;
    private static final String CONFIG_JSON_FILE = "configuration.json";

    // Single constructor to inject both dependencies
    public ConfigurationController(ConfigurationService configurationService, ObjectMapper objectMapper) {
        this.configurationService = configurationService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<String> saveConfigToJson(@RequestBody Configuration configuration) {
        try {
            // Validate the configuration
            InputValidation.validateConfiguration(configuration);

            // Write configuration to JSON file
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(CONFIG_JSON_FILE), configuration);
            return ResponseEntity.ok("Configuration saved as JSON successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Validation error: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error saving configuration to JSON: " + e.getMessage());
        }
    }

    @PostMapping("/start")
    public ResponseEntity<String> startSimulation() {
        configurationService.startSimulation();
        return ResponseEntity.ok("Simulation started.");
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopSimulation() {
        configurationService.stopSimulation();
        return ResponseEntity.ok("Simulation stopped.");
    }
}

