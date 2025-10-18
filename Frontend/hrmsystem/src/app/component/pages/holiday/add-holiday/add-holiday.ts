import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Holiday } from '../../../../model/holiday.model';
import { HolidayService } from '../../../../service/holiday-service';

@Component({
  selector: 'app-add-holiday',
  standalone: false,
  templateUrl: './add-holiday.html',
  styleUrl: './add-holiday.css'
})
export class AddHoliday implements OnInit {

  holidays: Holiday[] = [];
  newDate: string = '';
  newDescription: string = '';

  constructor(private holidayService: HolidayService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.loadHolidays();
  }

  loadHolidays() {
    this.holidayService.getAllHolidays().subscribe(data => {
      this.holidays = data;
      this.cdr.markForCheck();
    });
  }

  addHoliday() {
    if (!this.newDate || !this.newDescription) return;
    this.holidayService.addHoliday(this.newDate, this.newDescription).subscribe(() => {
      this.loadHolidays();
      this.newDate = '';
      this.newDescription = '';
    });
  }

  deleteHoliday(id: number) {
    this.holidayService.deleteHoliday(id).subscribe(() => {
      this.loadHolidays();
    });
  }

}
