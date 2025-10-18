import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { ClockService } from '../../../service/clock-service';
import { EmployeeService } from '../../../service/employee-service';
import { AuthService } from '../../../service/auth-service';
import { Subscription } from 'rxjs';
import { ClockData } from '../../../model/clockData.model';
import { Leave } from '../../../model/leave.model';
import { LeaveService } from '../../../service/leave-service';
import { DepartmentService } from '../../../service/department-service';

@Component({
  selector: 'app-admin-profile',
  standalone: false,
  templateUrl: './admin-profile.html',
  styleUrl: './admin-profile.css'
})
export class AdminProfile implements OnInit, OnDestroy {

  time24 = '00:00:00';
  date = '';
  private sub?: Subscription;

  emp: any; // admin is also an employee
  employees: any[] = [];
  depts: any[] = [];

  deptHeadLeaves: Leave[] = [];
  approvedLeaves: Leave[] = [];
  lastUpdatedDeptHeadLeaves: string = '';
  lastUpdatedApprovedLeaves: string = '';

  constructor(
    private authService: AuthService,
    private clockService: ClockService,
    private empService: EmployeeService,
    private leaveService: LeaveService,
    private deptService: DepartmentService,
    private cdr: ChangeDetectorRef
  ) { }




  ngOnInit(): void {
    // Clock polling
    this.sub = this.clockService.getClockStream(1000).subscribe((data: ClockData) => {
      this.time24 = data.time ?? '00:00:00';
      this.date = data.date ?? '';
      this.cdr.markForCheck();
    });

    this.loadDeptHeadLeaves();
    this.loadApprovedLeaves();
    // Load admin as employee
    this.loadAdminEmployeeProfile();
    // this.loadAllEmployee();
    this.loadAllDept();
  }





  //for department name lookup
  deptMap: { [id: number]: string } = {};

  loadAllDept(): void {
    this.deptService.getAll().subscribe({
      next: (data) => {
        this.depts = data;
        // Build a map: deptId -> deptName
        this.deptMap = {};
        this.depts.forEach(d => this.deptMap[d.id] = d.deptName);
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Failed to load depts', err)
    });
  }
  getDeptName(departmentId: number): string {
    return this.deptMap[departmentId] || 'Unknown';
  }
  //for department name lookup


  loadAdminEmployeeProfile(): void {
    this.empService.getProfile().subscribe({
      next: (data) => {
        this.emp = data;
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Failed to load admin profile', err)
    });
  }

  // loadDeptHeadLeaves(): void {
  //   this.leaveService.getDeptHeadLeaves().subscribe({
  //     next: (data) => {
  //       this.deptHeadLeaves = data
  //         .filter(l => l.status === 'PENDING')
  //         .sort((a, b) =>
  //           new Date(b.requestedDate).getTime() - new Date(a.requestedDate).getTime()
  //         );
  //       this.cdr.markForCheck();
  //     },
  //     error: (err) => console.error('Failed to load DeptHead leaves', err)
  //   });
  // }

  // loadApprovedLeaves(): void {
  //   this.leaveService.getAllLeaves().subscribe({
  //     next: (data) => {
  //       this.approvedLeaves = data
  //         .filter(l => l.status === 'APPROVED')
  //         .sort((a, b) =>
  //           new Date(b.approvalDate ?? '').getTime() - new Date(a.approvalDate ?? '').getTime()
  //         );
  //       this.cdr.markForCheck();
  //     },
  //     error: (err) => console.error('Failed to load approved leaves', err)
  //   });
  // }
  loadDeptHeadLeaves(): void {
    this.leaveService.getDeptHeadLeaves().subscribe({
      next: (data) => {
        this.deptHeadLeaves = data
          .filter(l => l.status === 'PENDING')
          .sort((a, b) =>
            new Date(b.requestedDate).getTime() - new Date(a.requestedDate).getTime()
          );
        this.lastUpdatedDeptHeadLeaves = new Date().toLocaleString(); // update time
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Failed to load DeptHead leaves', err)
    });
  }

  loadApprovedLeaves(): void {
    this.leaveService.getAllLeaves().subscribe({
      next: (data) => {
        this.approvedLeaves = data
          .filter(l => l.status === 'APPROVED')
          .sort((a, b) =>
            new Date(b.approvalDate ?? '').getTime() - new Date(a.approvalDate ?? '').getTime()
          );
        this.lastUpdatedApprovedLeaves = new Date().toLocaleString(); // update time
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Failed to load approved leaves', err)
    });
  }

  approve(id: number): void {
    this.leaveService.approveLeave(id).subscribe({
      next: () => {
        this.loadDeptHeadLeaves();
        this.loadApprovedLeaves();
      },
      error: (err) => console.error('Failed to approve leave', err)
    });
  }

  reject(id: number): void {
    this.leaveService.rejectLeave(id).subscribe({
      next: () => this.loadDeptHeadLeaves(),
      error: (err) => console.error('Failed to reject leave', err)
    });
  }


  getGreeting(): string {
    if (!this.time24) return 'Hello';
    const hour = parseInt(this.time24.split(':')[0], 10);
    if (hour >= 5 && hour < 12) return 'Good Morning';
    if (hour >= 12 && hour < 17) return 'Good Afternoon';
    if (hour >= 17 && hour < 21) return 'Good Evening';
    return 'Good Night';
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
  }

}
