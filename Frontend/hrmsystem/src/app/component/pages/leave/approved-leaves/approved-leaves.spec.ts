import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApprovedLeaves } from './approved-leaves';

describe('ApprovedLeaves', () => {
  let component: ApprovedLeaves;
  let fixture: ComponentFixture<ApprovedLeaves>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ApprovedLeaves]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ApprovedLeaves);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
