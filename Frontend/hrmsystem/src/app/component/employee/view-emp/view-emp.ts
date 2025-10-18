import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { EmployeeService } from '../../../service/employee-service';
import { Observable } from 'rxjs';
import { Employee } from '../../../model/employee.model';

@Component({
  selector: 'app-view-emp',
  standalone: false,
  templateUrl: './view-emp.html',
  styleUrl: './view-emp.css'
})
export class ViewEmp implements OnInit{

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

  
    

}
