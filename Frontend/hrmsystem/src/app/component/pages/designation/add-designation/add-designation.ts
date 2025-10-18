import { ChangeDetectorRef, Component } from '@angular/core';
import { Designation } from '../../../../model/Designation.model';
import { Department } from '../../../../model/Department.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DesignationService } from '../../../../service/designation-service';
import { DepartmentService } from '../../../../service/department-service';

@Component({
  selector: 'app-add-designation',
  standalone: false,
  templateUrl: './add-designation.html',
  styleUrl: './add-designation.css'
})
export class AddDesignation {

  designations: Designation[] = [];
  departments: Department[] = [];
  designationForm!: FormGroup;
  editMode = false;
  currentDesignationId?: number;

  constructor(
    private designationService: DesignationService,
    private fb: FormBuilder,
    private cdr: ChangeDetectorRef,
    private departmentService: DepartmentService, // Inject Department service
  ) { }

  ngOnInit(): void {
    this.loadCountries();
    this.loadDesignations();

    this.designationForm = this.fb.group({
      name: ['', Validators.required],
      department: this.fb.group({
        id: [null, Validators.required]
      })
    });
  }

  loadCountries(): void {
    this.departmentService.getAll().subscribe(data => {
      this.departments = data;
      this.cdr.markForCheck();
    });
  }

  loadDesignations(): void {
    this.designationService.getAll().subscribe(data => {
      this.designations = data;
      this.cdr.markForCheck();
    });
  }

  onSubmit(): void {
    if (this.designationForm.invalid) return;

    const designation: Designation = this.designationForm.value;

    if (this.editMode && this.currentDesignationId != null) {
      this.designationService.update(this.currentDesignationId, designation).subscribe(() => {
        this.loadDesignations();
        this.resetForm();
      });
    } else {
      this.designationService.create(designation).subscribe(() => {
        this.loadDesignations();
        this.resetForm();
      });
    }
  }

  onEdit(designation: Designation): void {
    this.editMode = true;
    this.currentDesignationId = designation.id;
    this.designationForm.patchValue({
      name: designation.name,
      department: { id: designation.department?.id }
    });
  }

  onDelete(id: number): void {
    if (confirm('Are you sure you want to delete this Designation?')) {
      this.designationService.delete(id).subscribe(() => {
        this.loadDesignations();
      });
    }
  }

  resetForm(): void {
    this.designationForm.reset();
    this.editMode = false;
    this.currentDesignationId = undefined;
  }

}
