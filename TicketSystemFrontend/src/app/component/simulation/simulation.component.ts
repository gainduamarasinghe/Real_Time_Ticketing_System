import { Component, OnInit, OnDestroy } from '@angular/core';
import { WebSocketService } from '../../services/web-socket.service';

@Component({
  selector: 'app-simulation',
  templateUrl: './simulation.component.html',
  styleUrls: ['./simulation.component.css'],
})
export class SimulationComponent implements OnInit, OnDestroy {
  messages: string[] = []; // To store WebSocket messages

  constructor(private webSocketService: WebSocketService) {}

  ngOnInit(): void {
    this.webSocketService.connect((message: string) => {
      this.messages.push(message); // Add received message to the array
    });
  }

  ngOnDestroy(): void {
    this.webSocketService.disconnect();
  }
}
