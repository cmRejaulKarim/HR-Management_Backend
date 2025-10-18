import { Component, OnInit } from '@angular/core';
import { Salary } from '../../../../model/salary.model';
import { SalaryService } from '../../../../service/salary-service';

@Component({
  selector: 'app-create-monthly-salary',
  standalone: false,
  templateUrl: './create-monthly-salary.html',
  styleUrl: './create-monthly-salary.css'
})
export class CreateMonthlySalary implements OnInit {


  salaries: Salary[] = [];
  year: number = new Date().getFullYear();
  month: number = new Date().getMonth() + 1; // JS months are 0-based
  loading = false;

  constructor(private salaryService: SalaryService) { }

  ngOnInit(): void {
    this.loadSalaries();
  }

  loadSalaries(): void {
    this.loading = true;
    this.salaryService.getAllSalariesByMonth(this.year, this.month).subscribe({
      next: (data) => {
        this.salaries = data;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
      }
    });
  }

  generateSalaryForAll(): void {
    this.salaryService.createSalaryForAll(this.year, this.month).subscribe({
      next: () => this.loadSalaries(),
      error: (err) => console.error(err)
    });
  }

  // approveSalary(salaryId: number): void {
  //   this.salaryService.approveSalary(salaryId).subscribe({
  //     next: () => this.loadSalaries(),
  //     error: (err) => console.error(err)
  //   });
  // }
}
