import { Employee } from "./employee.model";

export interface LeaveWithEmployee extends Leave {
    employee: Employee;
}

export interface Leave {
    id?: number;
    employee?: Employee; // Optional, can be populated when needed
    startDate: string;
    endDate: string;
    totalLeaveDays:number;
    reason:string;
    requestedDate: string;
    status:string; // PENDING, APPROVED, REJECTED
    approvalDate?: string;
}

// export interface LeaveWithEmployee {
//     id?: number;
//     startDate: string;
//     endDate: string;
//     totalLeaveDays: number;
//     reason: string;
//     requestedDate: string;
//     status: 'PENDING' | 'APPROVED' | 'REJECTED';
//     approvalDate?: string;

//     // Employee details
//     employeeId: number;
//     employeeName: string;
//     employeeEmail: string;
//     departmentName?: string;
// }