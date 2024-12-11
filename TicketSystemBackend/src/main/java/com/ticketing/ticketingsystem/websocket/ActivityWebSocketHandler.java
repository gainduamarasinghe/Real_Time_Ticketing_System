package com.ticketing.ticketingsystem.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.CloseStatus;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * WebSocket handler for managing WebSocket connections and broadcasting messages to all connected clients.
 */
@Component
public class ActivityWebSocketHandler extends TextWebSocketHandler {

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    /**
     * Handles a new WebSocket connection by adding the session to the list of active sessions.
     *
     * @param session the WebSocket session for the new connection.
     * @throws Exception if an error occurs during connection establishment.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("WebSocket connection established.");
    }

    /**
     * Handles the closure of a WebSocket connection by removing the session from the list of active sessions.
     *
     * @param session the WebSocket session that was closed.
     * @param status  the status of the connection closure.
     * @throws Exception if an error occurs during connection closure.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("WebSocket connection closed.");
    }

    /**
     * Processes incoming text messages from WebSocket clients.
     *
     * @param session the WebSocket session from which the message was received.
     * @param message the received text message.
     * @throws Exception if an error occurs while processing the message.
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received message: " + message.getPayload());
    }

    /**
     * Broadcasts a message to all connected WebSocket clients.
     *
     * @param message the message to broadcast.
     */
    public void broadcastMessage(String message) {
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
