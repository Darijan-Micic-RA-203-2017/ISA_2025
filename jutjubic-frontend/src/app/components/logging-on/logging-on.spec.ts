import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoggingOnComponent } from './logging-on';

describe('LoggingOnComponent', () => {
  let component: LoggingOnComponent;
  let fixture: ComponentFixture<LoggingOnComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoggingOnComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoggingOnComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
