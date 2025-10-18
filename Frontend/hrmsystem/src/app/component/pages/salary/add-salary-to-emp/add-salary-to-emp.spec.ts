import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddSalaryToEmp } from './add-salary-to-emp';

describe('AddSalaryToEmp', () => {
  let component: AddSalaryToEmp;
  let fixture: ComponentFixture<AddSalaryToEmp>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddSalaryToEmp]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddSalaryToEmp);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
