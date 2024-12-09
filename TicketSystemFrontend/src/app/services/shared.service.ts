import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SharedService {
  private pollingStatus = new BehaviorSubject<boolean>(false); // Default to not polling
  pollingStatus$ = this.pollingStatus.asObservable(); // Observable to be subscribed by components

  startPolling(): void {
    this.pollingStatus.next(true); // Emit `true` to start polling
  }

  stopPolling(): void {
    this.pollingStatus.next(false); // Emit `false` to stop polling
  }
}
