import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {config, Observable} from 'rxjs';

export interface Configuration {
  totalTickets: number | null;
  maxTicketCapacity: number | null;
  ticketReleaseRate: number | null;
  customerRetrievalRate: number | null;
}


@Injectable({
  providedIn: 'root',
})
export class TicketService {
  private baseUrl = 'http://localhost:8080/api/config';

  constructor(private http: HttpClient) {}
  configureSystem(configuration: Configuration): Observable<any> {
    return this.http.post(`${this.baseUrl}/configure`, configuration, {
      headers: {
        'Content-Type': 'application/json'
      },
      responseType: 'text'
    });
  }

  connectToWebSocket(): WebSocket {
    return new WebSocket('ws://localhost:8080/live-updates');
  }
}
