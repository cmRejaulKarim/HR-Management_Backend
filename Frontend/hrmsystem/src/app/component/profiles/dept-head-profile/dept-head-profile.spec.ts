import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeptHeadProfile } from './dept-head-profile';

describe('DeptHeadProfile', () => {
  let component: DeptHeadProfile;
  let fixture: ComponentFixture<DeptHeadProfile>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DeptHeadProfile]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeptHeadProfile);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
