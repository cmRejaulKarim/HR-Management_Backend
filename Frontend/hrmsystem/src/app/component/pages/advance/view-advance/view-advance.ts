import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { AdvanceSalary } from '../../../../model/advance.model';
import { AdvanceService } from '../../../../service/advance-service';

@Component({
  selector: 'app-view-advance',
  standalone: false,
  templateUrl: './view-advance.html',
  styleUrl: './view-advance.css'
})
export class ViewAdvance implements OnInit {


  advances: AdvanceSalary[] = [];
  loading = false;
  error: string | null = null;

  constructor(private advanceService: AdvanceService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.loadAdvances();
  }

  // Load all advance requests
  loadAdvances(): void {
    this.loading = true;
    this.error = null;
    this.advanceService.viewAllAdvanceRequests().subscribe({
      next: (data) => {
        this.advances = data;
        this.loading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.error = 'Failed to load advance requests';
        console.error(err);
        this.loading = false;
      }
    });
  }

  approve(adv: any): void {
    adv.status = 'APPROVED'; // disable instantly
    this.advanceService.approveAdvanceSalary(adv.id).subscribe({
      next: () => {
        // no need for loadAdvances(), row already updated
      },
      error: (err) => {
        console.error('Error approving advance', err);
        adv.status = 'PENDING'; // revert if failed
      }
    });
  }

  reject(adv: any): void {
    adv.status = 'REJECTED'; // disable instantly
    this.advanceService.rejectAdvanceSalary(adv.id).subscribe({
      next: () => {
        // no need for loadAdvances()
      },
      error: (err) => {
        console.error('Error rejecting advance', err);
        adv.status = 'PENDING'; // revert if failed
      }
    });
  }

  // // Approve request
  // approve(id: number): void {
  //   this.advanceService.approveAdvanceSalary(id).subscribe({
  //     next: () => {
  //       this.loadAdvances(); // refresh list
  //     },
  //     error: (err) => console.error('Error approving advance', err)
  //   });
  // }

  // // Reject request
  // reject(id: number): void {
  //   this.advanceService.rejectAdvanceSalary(id).subscribe({
  //     next: () => {
  //       this.loadAdvances(); // refresh list
  //     },
  //     error: (err) => console.error('Error rejecting advance', err)
  //   });
  // }
  
}
