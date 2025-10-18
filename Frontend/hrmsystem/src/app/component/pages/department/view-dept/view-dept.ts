import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Department } from '../../../../model/Department.model';
import { Designation } from '../../../../model/Designation.model';
import { DepartmentService } from '../../../../service/department-service';
import { DesignationService } from '../../../../service/designation-service';

@Component({
  selector: 'app-view-dept',
  standalone: false,
  templateUrl: './view-dept.html',
  styleUrl: './view-dept.css'
})
export class ViewDept implements OnInit {

  departments: Department[] = [];
  designations: Designation[] = [];

  selectedDepartmentId?: number;
  selectedDesignationId?: number;

  constructor(
    private departmentService: DepartmentService,
    private designationService: DesignationService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.loadDepartments();
  }

  loadDepartments(): void {
    this.departmentService.getAll().subscribe({
      next: (data) => {
        this.departments = data;
        this.cdr.markForCheck(); // ensure view updates
      },
      error: (err) => {
        console.error('Failed to load departments', err);
      }
    });
  }

  onDepartmentChange(): void {
    if (this.selectedDepartmentId) {
      this.designationService.getByDepartmentId(this.selectedDepartmentId).subscribe({
        next: (data) => (this.designations = data),
        error: (err) => console.error('Failed to load designations', err)
      });
    } else { 
      this.designations = [];
    }
    this.selectedDesignationId = undefined;
  }
}
