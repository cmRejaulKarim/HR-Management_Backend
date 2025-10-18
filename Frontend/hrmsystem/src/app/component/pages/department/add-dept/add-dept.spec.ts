import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDept } from './add-dept';

describe('AddDept', () => {
  let component: AddDept;
  let fixture: ComponentFixture<AddDept>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddDept]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddDept);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
