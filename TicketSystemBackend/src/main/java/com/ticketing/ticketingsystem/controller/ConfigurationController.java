package com.ticketing.ticketingsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketing.ticketingsystem.config.Configuration;
import com.ticketing.ticketingsystem.service.ConfigurationService;
import com.ticketing.ticketingsystem.validation.InputValidation;
import com.ticketing.ticketingsystem.websocket.ActivityWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

/**
 * REST controller for managing configuration-related operations in the ticketing system.
 * Provides endpoints for saving configurations, starting, and stopping the simulation.
 */
@RestController
@RequestMapping("/api/config")
@CrossOrigin(origins = "http://localhost:4200")
public class ConfigurationController {

    private final ConfigurationService configurationService;
    private final ObjectMapper objectMapper;

    @Autowired
    private final ActivityWebSocketHandler messagingTemplate;

    private static final String CONFIG_JSON_FILE = "configuration.json";

    /**
     * Constructs a ConfigurationController instance with the required services.
     *
     * @param configurationService the service handling configuration-related operations.
     * @param objectMapper         the ObjectMapper used for JSON operations.
     * @param messagingTemplate    the WebSocket handler for broadcasting messages.
     */
    public ConfigurationController(ConfigurationService configurationService, ObjectMapper objectMapper, ActivityWebSocketHandler messagingTemplate) {
        this.configurationService = configurationService;
        this.objectMapper = objectMapper;
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Saves the provided configuration to a JSON file and notifies clients via WebSocket.
     *
     * @param configuration the configuration object to save.
     * @return a ResponseEntity indicating success or failure.
     */
    @PostMapping("/configure")
    public ResponseEntity<String> saveConfigToJson(@RequestBody Configuration configuration) {
        try {
            // Validate the configuration
            InputValidation.validateConfiguration(configuration);

            // Save configuration to service
            configurationService.saveConfiguration(configuration);

            // Write configuration to JSON file
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(CONFIG_JSON_FILE), configuration);

            // Notify via WebSocket
            messagingTemplate.broadcastMessage("Configuration saved as JSON successfully.");

            return ResponseEntity.ok("Configuration saved as JSON successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Validation error: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error saving configuration to JSON: " + e.getMessage());
        }
    }

    /**
     * Starts the ticketing simulation and notifies clients via WebSocket.
     *
     * @return a ResponseEntity indicating success or failure.
     */
    @PostMapping("/start")
    public ResponseEntity<String> startSimulation() {
        try {
            configurationService.startSimulation();
            // Notify clients via WebSocket
            messagingTemplate.broadcastMessage("Ticketing System started.");
            return ResponseEntity.ok("Ticketing System started.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Error starting system: " + e.getMessage());
        }
    }

    /**
     * Stops the ticketing simulation and notifies clients via WebSocket.
     *
     * @return a ResponseEntity indicating success or failure.
     */
    @PostMapping("/stop")
    public ResponseEntity<String> stopSimulation() {
        try {
            configurationService.stopSimulation();
            // Notify clients via WebSocket
            messagingTemplate.broadcastMessage("System stopped.");
            return ResponseEntity.ok("System stopped.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Error stopping system: " + e.getMessage());
        }
    }
}
