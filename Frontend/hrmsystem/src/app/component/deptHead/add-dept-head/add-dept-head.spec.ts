import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDeptHead } from './add-dept-head';

describe('AddDeptHead', () => {
  let component: AddDeptHead;
  let fixture: ComponentFixture<AddDeptHead>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddDeptHead]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddDeptHead);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
