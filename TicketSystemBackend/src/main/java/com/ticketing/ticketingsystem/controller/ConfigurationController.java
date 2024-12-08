package com.ticketing.ticketingsystem.controller;

import com.ticketing.ticketingsystem.config.Configuration;
import com.ticketing.ticketingsystem.service.ConfigurationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")
@CrossOrigin(origins = "http://localhost:4200")
public class ConfigurationController {
    private final ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @PostMapping
    public ResponseEntity<String> saveConfiguration(@RequestBody Configuration configuration) {
        configurationService.saveConfiguration(configuration);
        return ResponseEntity.ok("Configuration saved successfully.");
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
