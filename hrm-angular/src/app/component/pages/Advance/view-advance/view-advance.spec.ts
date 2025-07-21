import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAdvance } from './view-advance';

describe('ViewAdvance', () => {
  let component: ViewAdvance;
  let fixture: ComponentFixture<ViewAdvance>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewAdvance]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewAdvance);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
