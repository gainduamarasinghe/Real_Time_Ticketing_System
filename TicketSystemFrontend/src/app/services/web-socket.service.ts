import { Injectable } from '@angular/core';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private stompClient: Client;
  private readonly socketUrl = 'http://localhost:8080/websocket'; // WebSocket endpoint
  private readonly topic = '/topic/simulationStatus'; // WebSocket topic

  constructor() {
    const socket = SockJS(this.socketUrl);
    this.stompClient = new Client({
      webSocketFactory: () => socket as any,
      debug: (str) => console.log(str), // Logs WebSocket messages
      reconnectDelay: 5000, // Reconnect every 5 seconds if disconnected
    });
  }

  connect(callback: (message: string) => void): void {
    this.stompClient.onConnect = () => {
      console.log('WebSocket connected');
      this.stompClient.subscribe(this.topic, (message) => {
        callback(message.body); // Pass the message body to the callback
      });
    };

    this.stompClient.onStompError = (frame) => {
      console.error('Broker reported error: ', frame.headers['message']);
    };

    this.stompClient.activate(); // Replaces the `connect()` method
  }

  disconnect(): void {
    if (this.stompClient && this.stompClient.active) {
      this.stompClient.deactivate(); // Replaces the `disconnect()` method
      console.log('WebSocket disconnected');
    }
  }
}

