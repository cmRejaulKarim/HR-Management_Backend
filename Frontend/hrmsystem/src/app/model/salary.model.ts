import { AdvanceSalary } from "./advance.model";
import { Employee } from "./employee.model";
import { Leave } from "./leave.model";

export interface Salary {
  id?: number;
  employee: Employee;
  month: string; // ISO string format like '2025-08'
  basicSalary: number;
  allowance: number;
  overtimeSalary?: number;
  totalSalary?: number;
  advanceDeduction?: AdvanceSalary;
  totalLeave?: Leave;
  leavePenalty?: number;
  netPay?: number;
}