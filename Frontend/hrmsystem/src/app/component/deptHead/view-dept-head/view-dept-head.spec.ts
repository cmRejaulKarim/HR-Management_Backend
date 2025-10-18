import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewDeptHead } from './view-dept-head';

describe('ViewDeptHead', () => {
  let component: ViewDeptHead;
  let fixture: ComponentFixture<ViewDeptHead>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewDeptHead]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewDeptHead);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
