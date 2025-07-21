import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddSalary } from './add-salary';

describe('AddSalary', () => {
  let component: AddSalary;
  let fixture: ComponentFixture<AddSalary>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddSalary]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddSalary);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
