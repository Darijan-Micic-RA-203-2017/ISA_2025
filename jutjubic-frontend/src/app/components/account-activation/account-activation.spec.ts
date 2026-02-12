import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountActivationComponent } from './account-activation';

describe('AccountActivationComponent', () => {
  let component: AccountActivationComponent;
  let fixture: ComponentFixture<AccountActivationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccountActivationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccountActivationComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
