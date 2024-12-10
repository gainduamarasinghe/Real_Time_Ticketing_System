import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { HeaderComponent } from '../header/header.component';

import {Configuration, TicketService} from '../../services/web-socket.service';

@Component({
  selector: 'app-config-card',
  templateUrl: './config-card.component.html',
  styleUrls: ['./config-card.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
})
export class ConfigCardComponent {
  config: Configuration = {
    totalTickets: null,
    maxPoolCapacity: null,
    ticketReleaseRate: null,
    customerRetrievalRate: null,
  };
  logs: string[] = [];

  isLoading = false; // For showing loading state

  constructor(private ticketService: TicketService) {}


  submitConfiguration(): void {
    this.isLoading = true;
    this.ticketService.configureSystem(this.config).subscribe({
      next: () => {
        alert('System configured successfully!');
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error details:', error);
        alert('Configuration failed: ' + error.message);
        this.isLoading = false;
      },
    });
  }
}
