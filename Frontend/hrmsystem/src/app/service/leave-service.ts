import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Leave } from '../model/leave.model';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class LeaveService {

  private baseUrl = environment.apiUrl + '/leave/';

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) { }

  // Get authorization headers if token exists
  private getAuthHeaders(): HttpHeaders {
    let headers = new HttpHeaders();
    if (isPlatformBrowser(this.platformId)) {
      const token = localStorage.getItem('authToken');
      if (token) {
        headers = headers.set('Authorization', 'Bearer ' + token);
      }
    }
    return headers;
  }

  // Add a new leave request
  addLeave(data: Leave): Observable<Leave> {
    return this.http.post<Leave>(`${this.baseUrl}add`, data, { headers: this.getAuthHeaders() });
  }

  // Get logged-in user's leaves
  getLeavesByUser(): Observable<Leave[]> {
    return this.http.get<Leave[]>(`${this.baseUrl}byEmp`, { headers: this.getAuthHeaders() });
  }

  // Get all leaves of a department for DeptHead (excluding DeptHead's own leaves)
  getLeavesByDept(): Observable<Leave[]> {
    return this.http.get<Leave[]>(`${this.baseUrl}byDept`, { headers: this.getAuthHeaders() });
  }

  // Get all leaves of DeptHeads (Admin only)
  getDeptHeadLeaves(): Observable<Leave[]> {
    return this.http.get<Leave[]>(`${this.baseUrl}ofDeptHeads`, { headers: this.getAuthHeaders() });
  }

  // Approve a leave
  approveLeave(id: number): Observable<Leave> {
    return this.http.put<Leave>(`${this.baseUrl}${id}/approve`, {}, { headers: this.getAuthHeaders() });
  }

  // Reject a leave
  rejectLeave(id: number): Observable<Leave> {
    return this.http.put<Leave>(`${this.baseUrl}${id}/reject`, {}, { headers: this.getAuthHeaders() });
  }

  // Delete leave (optional)
  deleteLeave(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}${id}`, { headers: this.getAuthHeaders() });
  }

  // Get all leaves (Admin)
  getAllLeaves(): Observable<Leave[]> {
    return this.http.get<Leave[]>(`${this.baseUrl}all`, { headers: this.getAuthHeaders() });
  }
  
  // Get yearly approved leave days for a specific employee
  getYearlyApprovedLeaveDays(empId: number, year: number): Observable<number> {
    return this.http.get<number>(
      `${this.baseUrl}approved/yearly?empId=${empId}&year=${year}`,
      { headers: this.getAuthHeaders() }
    );
  }
}
