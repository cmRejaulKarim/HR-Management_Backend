import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';
import { Employee } from '../model/employee.model';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {


  private baseUrl = environment.apiUrl + '/employee';
  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) { }

  registerEmployee(user: any, employee: any, photo: File): Observable<any> {
    const formData = new FormData();
    formData.append('user', JSON.stringify(user));
    formData.append('employee', JSON.stringify(employee));
    formData.append('photo', photo);

    return this.http.post(this.baseUrl + "/", formData);
  }

  getProfile(): Observable<Employee> {
    let headers = new HttpHeaders();

    if (isPlatformBrowser(this.platformId)) {
      const token = localStorage.getItem('authToken');
      if (token) {
        headers = headers.set('Authorization', 'Bearer ' + token);
        console.log(headers);
      }
    }
    console.log("Header from getProfile" , headers);

    return this.http.get<Employee>(`${this.baseUrl}/profile`, { headers });
  }
  getAllByDept(): Observable<Employee[]> {
    let headers = new HttpHeaders();

    if (isPlatformBrowser(this.platformId)) {
      const token = localStorage.getItem('authToken');
      if (token) {
        headers = headers.set('Authorization', 'Bearer ' + token);
      }
    }
    console.log("Header from getAllByDept" , headers);

    return this.http.get<Employee[]>(`${this.baseUrl}/byDept`, { headers });
  }

  getAll(): Observable<Employee[]> {
    let headers = new HttpHeaders();

    if (isPlatformBrowser(this.platformId)) {
      const token = localStorage.getItem('authToken');
      if (token) {
        headers = headers.set('Authorization', 'Bearer ' + token);
      }
    }

    return this.http.get<Employee[]>(`${this.baseUrl}/all`, { headers })
  }

  addSalary(empId: Number, salary: number, allowance: number): Observable<Employee> {
    let headers = new HttpHeaders();

    if (isPlatformBrowser(this.platformId)) {
      const token = localStorage.getItem('authToken');
      if (token) {
        headers = headers.set('Authorization', 'Bearer ' + token);
      }
    }


    return this.http.patch<Employee>(`${this.baseUrl}/${empId}/salary`, {
      basicSalary: salary,
      allowance: allowance
    }
      , { headers });
  }
}
