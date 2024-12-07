import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ControllerCardComponent } from './controller-card.component';

describe('ControllorCardComponent', () => {
  let component: ControllerCardComponent;
  let fixture: ComponentFixture<ControllerCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ControllerCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ControllerCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
