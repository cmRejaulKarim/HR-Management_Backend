import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAdvance } from './add-advance';

describe('AddAdvance', () => {
  let component: AddAdvance;
  let fixture: ComponentFixture<AddAdvance>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddAdvance]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddAdvance);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
