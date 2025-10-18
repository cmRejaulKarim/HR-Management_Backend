import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateMonthlySalary } from './create-monthly-salary';

describe('CreateMonthlySalary', () => {
  let component: CreateMonthlySalary;
  let fixture: ComponentFixture<CreateMonthlySalary>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreateMonthlySalary]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateMonthlySalary);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
