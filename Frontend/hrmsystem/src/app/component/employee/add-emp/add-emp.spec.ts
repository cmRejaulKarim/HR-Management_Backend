import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEmp } from './add-emp';

describe('AddEmp', () => {
  let component: AddEmp;
  let fixture: ComponentFixture<AddEmp>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddEmp]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddEmp);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
