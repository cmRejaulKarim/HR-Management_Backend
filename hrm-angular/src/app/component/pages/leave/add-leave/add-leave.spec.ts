import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddLeave } from './add-leave';

describe('AddLeave', () => {
  let component: AddLeave;
  let fixture: ComponentFixture<AddLeave>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddLeave]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddLeave);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
