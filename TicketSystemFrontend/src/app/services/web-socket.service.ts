import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private stompClient: Client;
  private readonly socketUrl = 'http://localhost:8080/websocket'; // WebSocket endpoint
  private readonly ticketCounterTopic = '/topic/ticketCounter'; // WebSocket topic for ticket counter

  constructor() {
    const socket = new SockJS(this.socketUrl);
    this.stompClient = new Client({
      webSocketFactory: () => socket as any,
      debug: (str) => console.log(str), // Logs WebSocket messages
      reconnectDelay: 5000, // Reconnect every 5 seconds if disconnected
    });
  }

  connect(callback: (counter: string) => void): void {
    this.stompClient.onConnect = () => {
      console.log('WebSocket connected');
      this.stompClient.subscribe("/topic/ticketCounter", (message) => {
        callback(message.body); // Pass the ticket counter to the callback
      });
    };

    this.stompClient.activate(); // Activate the WebSocket connection
  }
}
