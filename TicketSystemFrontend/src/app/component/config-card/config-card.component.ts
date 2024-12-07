import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {FormsModule} from '@angular/forms';

@Component({
  standalone: true,
  imports: [FormsModule],
  selector: 'app-config-card',
  templateUrl: './config-card.component.html',
  styleUrls: ['./config-card.component.css']
})
export class ConfigCardComponent {
  config = {
    totalAvailableTickets: 0,
    ticketReleaseRate: 0,
    customerRetrievalRate: 0,
    maximumPoolCapacity: 0
  };

  // constructor(private http: HttpClient) {}
  //
  // submitConfig() {
  //   this.http.post('http://localhost:8080/api/config', this.config).subscribe({
  //     next: (response) => {
  //       console.log('Configuration saved successfully:', response);
  //     },
  //     error: (error) => {
  //       console.error('Error saving configuration:', error);
  //     }
  //   });
  // }
}
