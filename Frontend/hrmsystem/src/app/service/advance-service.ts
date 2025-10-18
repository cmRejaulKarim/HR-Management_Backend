import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { Observable } from 'rxjs';
import { AdvanceSalary } from '../model/advance.model';
import { isPlatformBrowser } from '@angular/common';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AdvanceService {

  private baseUrl = environment.apiUrl + '/advanceSalary';

  constructor(private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object) { }

  private getAuthHeaders(contentType: string = 'application/json'): HttpHeaders {
    let headers = new HttpHeaders({ 'Content-Type': contentType });

    if (isPlatformBrowser(this.platformId)) {
      const token = localStorage.getItem('authToken');
      if (token) {
        headers = headers.set('Authorization', `Bearer ${token}`);
      }
    }

    return headers;
  }


  addAdvanceRequest(amount: number, reason: string): Observable<AdvanceSalary> {
    const headers = this.getAuthHeaders('application/x-www-form-urlencoded');
    const body = new HttpParams()
      .set('amount', amount.toString())
      .set('reason', reason);

    return this.http.post<AdvanceSalary>(`${this.baseUrl}/request`, body, { headers });
  }

 
  viewAdvanceRequestsByEmp(): Observable<AdvanceSalary[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<AdvanceSalary[]>(`${this.baseUrl}/ByEmp`, { headers });
  }

 
  viewAdvanceRequests(empId: number): Observable<AdvanceSalary[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<AdvanceSalary[]>(`${this.baseUrl}/employee/${empId}`, { headers });
  }


  viewAllAdvanceRequests(): Observable<AdvanceSalary[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<AdvanceSalary[]>(`${this.baseUrl}/all`, { headers });
  }
  
  //Approve AdvanceSalary
    approveAdvanceSalary(id: number): Observable<AdvanceSalary> {
      return this.http.put<AdvanceSalary>(`${this.baseUrl}/${id}/approve`, {}, { headers: this.getAuthHeaders() });
    }
  
    //Reject AdvanceSalary
    rejectAdvanceSalary(id: number): Observable<AdvanceSalary> {
      return this.http.put<AdvanceSalary>(`${this.baseUrl}/${id}/reject`, {}, { headers: this.getAuthHeaders() });
    }


  getApprovedAdvance(empId: number, date: string): Observable<AdvanceSalary> {
    const headers = this.getAuthHeaders();
    const params = new HttpParams().set('date', date);
    return this.http.get<AdvanceSalary>(`${this.baseUrl}/employee/${empId}/approved`, { params, headers });
  }


  getAdvancesInPeriod(empId: number, startDate: string, endDate: string): Observable<AdvanceSalary[]> {
    const headers = this.getAuthHeaders();
    const params = new HttpParams()
      .set('startDate', startDate)
      .set('endDate', endDate);

    return this.http.get<AdvanceSalary[]>(`${this.baseUrl}/employee/${empId}/period`, { params, headers });
  }

}
