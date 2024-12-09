import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-controller-card',
  standalone: true,
  templateUrl: './controller-card.component.html',
  styleUrls: ['./controller-card.component.css'],
})
export class ControllerCardComponent {
  private apiUrl = 'http://localhost:8080/api/config';

  constructor(private http: HttpClient) {}

  startSystem(): void {
    this.http.post(`${this.apiUrl}/start`, {}, { responseType: 'text' }).subscribe({
      next: (response) => {
        console.log('Response:', response);
        // alert('System started successfully.');
      },
      error: (err) => {
        console.error('Error details:', err);
        alert('Error starting the system. Please try again.');
      },
    });
  }

  stopSystem(): void {
    this.http.post(`${this.apiUrl}/stop`, {}, { responseType: 'text' }).subscribe({
      next: (response) => {
        console.log('Response:', response);
        alert('System stopped successfully.');
      },
      error: (err) => {
        console.error('Error details:', err);
        alert('Error stopping the system. Please try again.');
      },
    });
  }
}
