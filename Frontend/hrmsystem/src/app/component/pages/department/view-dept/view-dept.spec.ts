import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewDept } from './view-dept';

describe('ViewDept', () => {
  let component: ViewDept;
  let fixture: ComponentFixture<ViewDept>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewDept]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewDept);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
