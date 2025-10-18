import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewEmpDetails } from './view-emp-details';

describe('ViewEmpDetails', () => {
  let component: ViewEmpDetails;
  let fixture: ComponentFixture<ViewEmpDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewEmpDetails]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewEmpDetails);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
