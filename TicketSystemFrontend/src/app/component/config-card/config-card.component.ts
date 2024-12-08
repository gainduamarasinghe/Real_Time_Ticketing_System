import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {FormsModule} from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-config-card',
  imports: [FormsModule],
  templateUrl: './config-card.component.html',
  styleUrls: ['./config-card.component.css'],
})
export class ConfigCardComponent {
  totalTickets: number = 0;
  ticketReleaseRate: number = 0;
  customerRetrievalRate: number = 0;
  maxPoolCapacity: number = 0;

  private apiUrl = 'http://localhost:8080/api/config';

  constructor(private http: HttpClient) {}

  submitConfiguration(): void {
    const configuration = {
      maxTicketCapacity: this.maxPoolCapacity,
      totalTickets: this.totalTickets,
      ticketReleaseRate: this.ticketReleaseRate,
      customerRetrievalRate: this.customerRetrievalRate,
    };

    this.http.post(`${this.apiUrl}`, configuration, { responseType: 'text' }).subscribe({
      next: (response) => {
        console.log('Response:', response);
        alert('Configuration saved successfully.');
      },
      error: (err) => {
        console.error('Error details:', err);
        alert('Error saving configuration. Please try again.');
      },
    });
  }

}
