import { Employee } from "./employee.model";

export type AdvanceSalaryStatus = 'PENDING' | 'APPROVED' | 'REJECTED';

export interface AdvanceSalary {
  id?: number;
  employee: Employee;
  amount: number;
  reason: string;
  requestDate: string; 
  status: AdvanceSalaryStatus;
  approvalDate?: string | null; 
}
