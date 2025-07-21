import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewSalary } from './view-salary';

describe('ViewSalary', () => {
  let component: ViewSalary;
  let fixture: ComponentFixture<ViewSalary>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewSalary]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewSalary);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
