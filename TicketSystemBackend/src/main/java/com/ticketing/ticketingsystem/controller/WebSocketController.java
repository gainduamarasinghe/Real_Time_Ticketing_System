package com.ticketing.ticketingsystem.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Sends a message to all subscribers about simulation updates.
     */
    public void sendSimulationUpdate(String message) {
        messagingTemplate.convertAndSend("/topic/simulationStatus", message);
    }
}
