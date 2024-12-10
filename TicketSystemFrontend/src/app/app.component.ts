import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {HeaderComponent} from './component/header/header.component';
import {FooterComponent} from './component/footer/footer.component';
import {ControllerCardComponent} from './component/controller-card/controller-card.component';
import {ConfigCardComponent} from './component/config-card/config-card.component';
import {LoggerCardComponent} from './component/logger-card/logger-card.component';
import {GraphComponent} from './component/graph/graph.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HeaderComponent, FooterComponent, ControllerCardComponent, ConfigCardComponent, LoggerCardComponent, GraphComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'TicketSystemFrontend';
}
