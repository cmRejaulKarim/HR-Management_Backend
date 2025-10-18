import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { LeaveService } from '../../../../service/leave-service';
import { Leave } from '../../../../model/leave.model';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-view-leave',
  standalone: false,
  templateUrl: './view-leave.html',
  styleUrl: './view-leave.css'
})
export class ViewLeave implements OnInit {

  leaves: Leave[] = [];
  loading: boolean = false;
  error: string | null = null;


  yearlyApprovedMap: { [empId: number]: number } = {};
  year: number = new Date().getFullYear();


  constructor(
    private leaveService: LeaveService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.loadLeaves();
    // this.loadYearlyApprovedLeaves();
  }


  loadLeavesWithYearlyApproved() {
    this.leaveService.getAllLeaves().subscribe({
      next: (leaves) => {
        this.leaves = leaves;

        // Get unique employee IDs
        const empIds = Array.from(new Set(leaves.map(l => l.employee?.id))).filter(id => id != null) as number[];

        // For each employee, get yearly approved leaves
        const requests = empIds.map(empId =>
          this.leaveService.getYearlyApprovedLeaveDays(empId, this.year)
        );

        forkJoin(requests).subscribe({
          next: (results) => {
            empIds.forEach((empId, index) => {
              this.yearlyApprovedMap[empId] = results[index];
            });
          },
          error: (err) => console.error(err)
        });
      },
      error: (err) => console.error(err)
    });
  }

  getYearlyApproved(empId: number | undefined): number {
    if (!empId) return 0;  // handle undefined
    return this.yearlyApprovedMap[empId] || 0;
  }

  loadLeaves(): void {
    this.loading = true;
    this.leaveService.getLeavesByDept().subscribe({
      next: (data) => {
        this.leaves = data;
        console.log(data);
        this.loading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        this.error = 'Failed to load leaves';
        this.loading = false;
      }
    });
  }

  approve(id: number): void {
    this.leaveService.approveLeave(id).subscribe({
      next: (updated) => {
        alert(`Leave ID ${updated.id} approved`);
        this.loadLeaves();
      },
      error: () => alert("Failed to approve leave")
    });
  }

  reject(id: number): void {
    this.leaveService.rejectLeave(id).subscribe({
      next: (updated) => {
        alert(`Leave ID ${updated.id} rejected`);
        this.loadLeaves();
      },
      error: () => alert("Failed to reject leave")
    });
  }

}
