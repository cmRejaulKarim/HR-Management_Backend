import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Holiday } from '../../../../model/holiday.model';
import { HolidayService } from '../../../../service/holiday-service';
import { ClockService } from '../../../../service/clock-service';
import { Subscription } from 'rxjs';
import { ClockData } from '../../../../model/clockData.model';

@Component({
  selector: 'app-view-holiday',
  standalone: false,
  templateUrl: './view-holiday.html',
  styleUrl: './view-holiday.css'
})
export class ViewHoliday implements OnInit {

  holidays: Holiday[] = [];

  groupBy: 'month' | 'year' = 'month'; // default view
  // groupedHolidays: { [key: string]: Holiday[] } = {};
   groupedHolidays: { [key: string]: Holiday[] } = {};

  // server values
  time24 = '00:00:00';
  date = '';
  private sub?: Subscription;

  monthNames = [
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
  ];


  constructor(
    private holidayService: HolidayService,
    private clockService: ClockService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    // Clock polling
    this.sub = this.clockService.getClockStream(1000).subscribe((data: ClockData) => {
      this.time24 = data.time ?? '00:00:00';
      this.date = data.date ?? '';
      this.cdr.markForCheck();
    });

    this.loadHolidays();
  }

   setGroupBy(mode: 'month' | 'year') {
    if (this.groupBy !== mode) {
      this.groupBy = mode;
      this.loadHolidays();
    }
  }

  loadHolidays() {
    this.holidayService.getAllHolidays().subscribe(data => {
      const sorted = data.sort((a, b) => new Date(a.date).getTime() - new Date(b.date).getTime());

      const grouped: { [key: string]: Holiday[] } = {};

      for (const holiday of sorted) {
        const dateObj = new Date(holiday.date);

        let key: string;
        // if (this.groupBy === 'month') {
        //   key = `${dateObj.getFullYear()}-${dateObj.toLocaleString('default', { month: 'long' })}`;
        // } else {
        //   key = `${dateObj.getFullYear()}`;
        // }
        if (this.groupBy === 'month') {
          const monthIndex = dateObj.getMonth(); // 0–11
          key = `${dateObj.getFullYear()}-${monthIndex}`;
        } else {
          key = `${dateObj.getFullYear()}`;
        }

        if (!grouped[key]) {
          grouped[key] = [];
        }

        grouped[key].push(holiday);
      }

      this.groupedHolidays = grouped;
      this.cdr.markForCheck();
    });
  }

}
