import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  standalone: true,
  selector: 'app-display-card',
  templateUrl: './display-card.component.html',
  styleUrls: ['./display-card.component.css'],
})
// export class DisplayCardComponent implements OnInit, OnDestroy {
//   ticketCounter: string = '0/0'; // Default value
//   private intervalId: any;
//   private readonly apiUrl = 'http://localhost:8080/api/config/info'; // API endpoint
//
//   constructor(private http: HttpClient) {}
//
//   ngOnInit(): void {
//     // Set an interval to call the API every 1 ms
//     this.intervalId = setInterval(() => {
//       this.http.get(this.apiUrl, { responseType: 'text' }).subscribe({
//         next: (data: string) => {
//           this.ticketCounter = data; // Update the ticket counter with API response
//         },
//         error: (err) => {
//           console.error('Error fetching ticket info:', err);
//         },
//       });
//     }, 1); // Call every 1 ms
//   }
//
//   ngOnDestroy(): void {
//     // Clear the interval when the component is destroyed
//     if (this.intervalId) {
//       clearInterval(this.intervalId);
//     }
//   }
// }
export class DisplayCardComponent{}
