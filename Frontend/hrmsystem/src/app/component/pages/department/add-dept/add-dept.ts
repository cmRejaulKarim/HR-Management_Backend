import { ChangeDetectorRef, Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DepartmentService } from '../../../../service/department-service';
import { Department } from '../../../../model/Department.model';

@Component({
  selector: 'app-add-dept',
  standalone: false,
  templateUrl: './add-dept.html',
  styleUrl: './add-dept.css'
})
export class AddDept {


  departments: Department[] = [];
  departmentForm!: FormGroup;
  editMode = false;
  editId?: number;

  constructor(
    private departmentService: DepartmentService,
     private fb: FormBuilder,
      private cdr: ChangeDetectorRef
    ) {}

  ngOnInit(): void {
    this.loadCountries();
    this.departmentForm = this.fb.group({
      name: ['', Validators.required]
    });
  }

 loadCountries(): void {
  this.departmentService.getAll().subscribe(data => {
    this.departments = data;
    this.cdr.markForCheck();
  });
}


  onSubmit(): void {
    if (this.departmentForm.invalid) return;

    const departmentData: Department = this.departmentForm.value;

    if (this.editMode && this.editId !== undefined) {
      this.departmentService.update(this.editId, departmentData).subscribe(() => {
        this.loadCountries();
        this.resetForm();
        this.cdr.markForCheck();
      });
    } else {
      this.departmentService.create(departmentData).subscribe(() => {
        this.loadCountries();
        this.resetForm();
        this.cdr.markForCheck();
      });
    }
  }

  onEdit(Department: Department): void {
    this.editMode = true;
    this.editId = Department.id;
    this.departmentForm.patchValue({
      name: Department.name
    });
    this.cdr.markForCheck();
  }

  onDelete(id?: number): void {
    if (id && confirm('Are you sure you want to delete this Department?')) {
      this.departmentService.delete(id).subscribe(() => {
        this.loadCountries();
        this.cdr.markForCheck();
      });
    }
  }

  resetForm(): void {
    this.editMode = false;
    this.editId = undefined;
    this.departmentForm.reset();
  }

}
