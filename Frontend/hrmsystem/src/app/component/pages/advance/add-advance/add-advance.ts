import { Component, Input, OnInit } from '@angular/core';
import { AdvanceSalary } from '../../../../model/advance.model';
import { AdvanceService } from '../../../../service/advance-service';

@Component({
  selector: 'app-add-advance',
  standalone: false,
  templateUrl: './add-advance.html',
  styleUrl: './add-advance.css'
})
export class AddAdvance implements OnInit{

  @Input() emp: any;     // From EmpProfile
  @Input() date!: string; // Also from EmpProfile (yyyy-MM-dd)

  empAdvanceList: AdvanceSalary[] = [];
  advance: Partial<AdvanceSalary> = {};
  showAdvanceForm: boolean = false;

  constructor(private advanceService: AdvanceService) {}

  ngOnInit(): void {
    this.loadAdvanceRequests();
  }

  loadAdvanceRequests(): void {
    this.advanceService.viewAdvanceRequestsByEmp().subscribe({
      next: (data) => this.empAdvanceList = data,
      error: (err) => console.error('Error fetching advance requests', err)
    });
  }

  toggleAdvanceForm(): void {
    this.showAdvanceForm = true;
  }

  cancelAdvance(): void {
    this.showAdvanceForm = false;
    this.advance = {};
  }

  hasRequestedThisMonth(): boolean {
    const today = new Date(this.date);
    return this.empAdvanceList.some(adv => {
      const advDate = new Date(adv.requestDate || '');
      return advDate.getFullYear() === today.getFullYear() &&
             advDate.getMonth() === today.getMonth();
    });
  }

  submitAdvanceRequest(): void {
    const amount = this.advance.amount;

    if (!amount || amount <= 0) {
      alert('Please enter a valid amount');
      return;
    }

    if (amount > this.emp?.basicSalary) {
      alert(`Requested amount exceeds your monthly salary of ₹${this.emp.basicSalary}`);
      return;
    }

    if (this.hasRequestedThisMonth()) {
      alert('You have already submitted an advance request this month.');
      return;
    }

    this.advanceService.addAdvanceRequest(amount, this.advance.reason || '').subscribe({
      next: (newRequest) => {
        this.empAdvanceList.unshift(newRequest);
        this.cancelAdvance();
      },
      error: (err) => {
        console.error('Failed to submit advance request', err);
        alert('Failed to submit request. Please try again later.');
      }
    });
  }

}
