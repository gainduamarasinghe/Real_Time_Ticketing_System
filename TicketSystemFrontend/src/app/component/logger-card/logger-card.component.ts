import { Component, OnInit } from '@angular/core';
import { WebSocketService } from '../../services/web-socket.service';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-logger-card',
  imports: [CommonModule],
  templateUrl: './logger-card.component.html',
  styleUrls: ['./logger-card.component.css'],
})
// export class LoggerCardComponent implements OnInit {
//   logs: string[] = []; // Array to store logs
//
//   constructor(private webSocketService: WebSocketService) {}
//
//   ngOnInit(): void {
//     try {
//       // Connect to the WebSocket and subscribe to updates
//       this.webSocketService.connect((message: string) => {
//         this.logs.push(message); // Push each message to the logs array
//       });
//     } catch (err) {
//       console.error('Error connecting to WebSocket:', err);
//     }
//   }
// }
export class LoggerCardComponent{

}
