import { ComponentFixture, TestBed } from '@angular/core/testing';
import {TicketService} from '../../services/web-socket.service';
import {LoggerCardComponent} from './logger-card.component';


describe('LoggerCardComponent', () => {
  let component: LoggerCardComponent;
  let fixture: ComponentFixture<LoggerCardComponent>;
  let mockTicketService: jasmine.SpyObj<TicketService>;

  beforeEach(async () => {
    mockTicketService = jasmine.createSpyObj('TicketService', ['connectToWebSocket']);
    mockTicketService.connectToWebSocket.and.returnValue({
      onmessage: null,
      close: jasmine.createSpy('close'),
    } as unknown as WebSocket);

    await TestBed.configureTestingModule({
      declarations: [LoggerCardComponent],
      providers: [{ provide: TicketService, useValue: mockTicketService }],
    }).compileComponents();

    fixture = TestBed.createComponent(LoggerCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should establish WebSocket connection on init', () => {
    expect(mockTicketService.connectToWebSocket).toHaveBeenCalled();
  });

  it('should close WebSocket on destroy', () => {
    component.ngOnDestroy();
    expect(mockTicketService.connectToWebSocket().close).toHaveBeenCalled();
  });
});
