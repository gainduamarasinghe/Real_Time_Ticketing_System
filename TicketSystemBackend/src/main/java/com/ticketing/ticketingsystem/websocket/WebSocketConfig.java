package com.ticketing.ticketingsystem.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Configures WebSocket support for the application.
 * Registers WebSocket handlers and their associated endpoints.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ActivityWebSocketHandler activityWebSocketHandler;

    /**
     * Constructs a WebSocketConfig instance with the specified WebSocket handler.
     *
     * @param activityWebSocketHandler the WebSocket handler for managing WebSocket connections.
     */
    public WebSocketConfig(ActivityWebSocketHandler activityWebSocketHandler) {
        this.activityWebSocketHandler = activityWebSocketHandler;
    }

    /**
     * Registers WebSocket handlers and their endpoints.
     *
     * @param registry the WebSocketHandlerRegistry for registering handlers and their configurations.
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(activityWebSocketHandler, "/live-updates").setAllowedOrigins("*");
    }
}
