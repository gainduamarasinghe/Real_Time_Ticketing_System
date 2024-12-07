import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {FormsModule} from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-config-card',
  imports: [FormsModule],
  templateUrl: './config-card.component.html',
  styleUrls: ['./config-card.component.css']
})
export class ConfigCardComponent {
  totalTickets!: number;
  ticketReleaseRate!: number;
  customerRetrievalRate!: number;
  maxPoolCapacity!: number;

  constructor(private http: HttpClient) {}

  // Method to send configuration data to the backend
  submitConfiguration() {
    const config = {
      totalTickets: this.totalTickets,
      ticketReleaseRate: this.ticketReleaseRate,
      customerRetrievalRate: this.customerRetrievalRate,
      maxPoolCapacity: this.maxPoolCapacity,
    };

    this.http.post('http://localhost:8080/api/config', config)
      .subscribe(
        response => console.log('Configuration saved:', response),
        error => console.error('Error saving configuration:', error)
      );
  }
}
