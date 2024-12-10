import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class TicketService {
  private baseUrl = 'http://localhost:8080/ticket-system';

  constructor(private http: HttpClient) {}


  connectToWebSocket(): WebSocket {
    return new WebSocket('ws://localhost:8080/live-updates');
  }
}
