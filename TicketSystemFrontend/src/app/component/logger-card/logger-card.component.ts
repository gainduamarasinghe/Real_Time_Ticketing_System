import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import {TicketService} from '../../services/web-socket.service';


@Component({
  selector: 'app-logger-card',
  templateUrl: './logger-card.component.html',
  styleUrls: ['./logger-card.component.css'],
  standalone: true,
  imports: [CommonModule], // Import CommonModule to use *ngFor and *ngIf
})
export class LoggerCardComponent implements OnInit, OnDestroy {
  logs: string[] = [];
  liveUpdates: string[] = [];
  private socket: WebSocket | null = null;

  constructor(private ticketService: TicketService) {}

  ngOnInit(): void {
    this.socket = this.ticketService.connectToWebSocket();

    this.socket.onmessage = (event) => {
      const message = event.data;
      this.liveUpdates.push(message);

      // Optional: Limit the number of updates displayed
      if (this.liveUpdates.length > 1000) {
        this.liveUpdates.shift();
      }
    };

    this.socket.onerror = (error) => {
      console.error('WebSocket error:', error);
    };
  }

  ngOnDestroy(): void {
    if (this.socket) {
      this.socket.close();
    }
  }
}
