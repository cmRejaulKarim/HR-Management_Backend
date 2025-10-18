import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { Observable } from 'rxjs';
import { Salary } from '../model/salary.model';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class SalaryService {

  private baseUrl = environment.apiUrl + '/salary/';

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) { }

  // Auth headers
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

  // 1️ Generate salary for a single employee
  createSalary(empId: number, year: number, month: number): Observable<Salary> {
    return this.http.post<Salary>(
      `${this.baseUrl}create?empId=${empId}&year=${year}&month=${month}`,
      {},
      { headers: this.getAuthHeaders() }
    );
  }

  // 2️ Generate salary for all employees for a month
  createSalaryForAll(year: number, month: number): Observable<Salary[]> {
    return this.http.post<Salary[]>(
      `${this.baseUrl}createAll?year=${year}&month=${month}`,
      {},
      { headers: this.getAuthHeaders() }
    );
  }

  // 3️ Get salary by ID
  getSalaryById(id: number): Observable<Salary> {
    return this.http.get<Salary>(
      `${this.baseUrl}${id}`,
      { headers: this.getAuthHeaders() }
    );
  }

  // 4️ Get all salaries for a specific month (Admin)
  getAllSalariesByMonth(year: number, month: number): Observable<Salary[]> {
    return this.http.get<Salary[]>(
      `${this.baseUrl}all?year=${year}&month=${month}`,
      { headers: this.getAuthHeaders() }
    );
  }
  
}
