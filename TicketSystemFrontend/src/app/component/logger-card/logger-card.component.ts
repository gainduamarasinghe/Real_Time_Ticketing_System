import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {CommonModule} from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-logger-card',
  templateUrl: './logger-card.component.html',
  styleUrls: ['./logger-card.component.css'],
  imports: [CommonModule]
})
export class LoggerCardComponent implements OnInit, OnDestroy {
  logs: string[] = [];  // Array to store logs
  private intervalId: any;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    // Start the interval when the component loads
    this.intervalId = setInterval(() => {
      this.getLogsFromApi(); // Fetch logs every second
    }, 1000); // 1 second interval
  }

  ngOnDestroy(): void {
    // Clear the interval when the component is destroyed
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }

  // Function to fetch logs from the backend API
  getLogsFromApi(): void {
    this.http.get('http://localhost:8080/api/config/logs', { responseType: 'text' }).subscribe(
      (logs: string) => {
        // Split the logs by lines and add them to the logs array
        const logLines = logs.split('\n');
        logLines.forEach((line) => {
          this.logs.push(line);  // Add each log line to the logs array
        });
      },
      (error) => {
        console.error('Error fetching logs', error);
      }
    );
  }
}
