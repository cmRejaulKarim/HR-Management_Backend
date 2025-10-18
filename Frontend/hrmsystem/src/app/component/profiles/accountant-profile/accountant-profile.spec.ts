import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountantProfile } from './accountant-profile';

describe('AccountantProfile', () => {
  let component: AccountantProfile;
  let fixture: ComponentFixture<AccountantProfile>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AccountantProfile]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccountantProfile);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
