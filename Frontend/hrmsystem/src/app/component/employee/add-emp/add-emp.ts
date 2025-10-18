import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmployeeService } from '../../../service/employee-service';
import { ClockService } from '../../../service/clock-service';
import { ClockData } from '../../../model/clockData.model';
import { Subscription } from 'rxjs';
import { DepartmentService } from '../../../service/department-service';
import { DesignationService } from '../../../service/designation-service';
import { Department } from '../../../model/Department.model';
import { Designation } from '../../../model/Designation.model';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-add-emp',
  standalone: false,
  templateUrl: './add-emp.html',
  styleUrl: './add-emp.css'
})
export class AddEmp implements OnInit {

  date = '';
  private sub?: Subscription;
  userForm: FormGroup;
  employeeForm: FormGroup;
  photoFile!: File;
  message: string = '';


  departments: Department[] = [];
  designations: Designation[] = [];

  filteredDesignation: Designation[] = [];
  selectedDepartmentId?: number;
  selectedDesignationId?: number;


  constructor(
    private fb: FormBuilder,
    private employeeService: EmployeeService,
    private clockService: ClockService,
    private cdr: ChangeDetectorRef,
    private deptService: DepartmentService,
    private desService: DesignationService,
  ) {
    this.userForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', Validators.required],
      password: ['', Validators.required]
    });

    this.employeeForm = this.fb.group({
      gender: ['', Validators.required],
      address: ['', Validators.required],
      dateOfBirth: ['', Validators.required],
      department: ['', Validators.required],
      designation: ['', Validators.required],
    });
  }
  ngOnInit(): void {
    this.sub = this.clockService.getClockStream(1000).subscribe((data: ClockData) => {
      this.date = data.date ?? '';
      this.cdr.markForCheck();
    });

    this.deptService.getAll().subscribe({
      next: (data) => {
        this.departments = data;   // use departments
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Failed to load departments', err)
    });

    this.employeeForm.get('designation')?.valueChanges.subscribe((desId) => {
      this.selectedDesignationId = desId;
      console.log('Selected designation ID updated to:', this.selectedDesignationId);
    });
  }


  onDeptChange() {
    const selectedDeptId = this.employeeForm.value.department;
    if (selectedDeptId) {
      this.desService.getByDepartmentId(selectedDeptId).subscribe({
        next: (data) => {
          setTimeout(() => {            // prevents NG0100
            this.filteredDesignation = data;
            console.log('Filtered designations:', this.filteredDesignation);
            this.cdr.markForCheck();
          });
        },
        error: (err) => console.error('Failed to load designations', err)
      });
    } else {
      this.filteredDesignation = [];
    }
  }

  onPhotoSelected(event: any): void {
    if (event.target.files.length > 0) {
      this.photoFile = event.target.files[0];
      console.log('Selected file:', this.photoFile);
    }
  }

  onSubmit(): void {
    if (!this.photoFile) {
      this.message = 'Please upload a photo.';
      return;
    }
    if (this.userForm.invalid || this.employeeForm.invalid) {
      this.message = 'Please fill out all required fields.';
      return;
    }

    const user = {
      name: this.userForm.value.name,
      email: this.userForm.value.email,
      phone: this.userForm.value.phone,
      password: this.userForm.value.password,
      role: 'EMPLOYEE' // adjust if necessary
    };

    const employee = {
      name: this.userForm.value.name,
      email: this.userForm.value.email,
      phone: this.userForm.value.phone,
      gender: this.employeeForm.value.gender,
      address: this.employeeForm.value.address,
      dateOfBirth: formatDate(this.employeeForm.value.dateOfBirth, 'yyyy-MM-dd', 'en'),
      departmentId: this.employeeForm.value.department,   // just the ID
      designationId: this.employeeForm.value.designation, // just the ID
      // department: { id: this.employeeForm.value.department },   // ✅ Wrap in object
      // designation: { id: this.employeeForm.value.designation }, // ✅ Wrap in object
      joiningDate: this.date, // use the current date
    };

    this.employeeService.registerEmployee(user, employee, this.photoFile).subscribe({
      next: res => {
        this.message = res.Message || 'Registration successful!';
        this.userForm.reset();
        this.employeeForm.reset();
        this.photoFile = undefined!;
      },
      error: err => {
        this.message = 'Registration failed: ' + (err.error?.Message || err.message);
      }
    });
  }


}
