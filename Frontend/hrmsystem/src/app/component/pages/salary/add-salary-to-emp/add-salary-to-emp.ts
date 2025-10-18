import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Employee } from '../../../../model/employee.model';
import { Observable } from 'rxjs';
import { EmployeeService } from '../../../../service/employee-service';

@Component({
  selector: 'app-add-salary-to-emp',
  standalone: false,
  templateUrl: './add-salary-to-emp.html',
  styleUrl: './add-salary-to-emp.css'
})
export class AddSalaryToEmp implements OnInit{

  employees!: Observable<Employee[]>;

  constructor(
    private empService: EmployeeService,
    private cdr : ChangeDetectorRef

  ){}

  ngOnInit(): void {
    this.loadAllEmployee();
    this.cdr.markForCheck();
  }
   
  loadAllEmployee(){
   this.employees= this.empService.getAll();
   this.cdr.markForCheck();
  }

  promptAddSalary(empId: Number): void {
    const salaryInput = prompt('Enter new salary:');
    const allowanceInput = prompt('Enter new allowance:');

    if (salaryInput !== null && allowanceInput !== null) {
      const newSalary = parseFloat(salaryInput);
      const newAllowance = parseFloat(allowanceInput);

      if (!isNaN(newSalary) && newSalary >= 0 && !isNaN(newAllowance) && newAllowance >= 0) {
        this.empService.addSalary(empId, newSalary, newAllowance).subscribe({
          next: (res) => {
            alert('Salary & Allowance updated successfully.');
            this.loadAllEmployee();
          },
          error: (err) => {
            console.error(err);
            alert('Failed to update salary & allowance.');
          }
        });
      } else {
        alert('Invalid input.');
      }
    }
  }

}

