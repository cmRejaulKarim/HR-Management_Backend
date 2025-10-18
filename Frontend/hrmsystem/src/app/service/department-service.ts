import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Department } from '../model/Department.model';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

   private baseUrl = environment.apiUrl + '/department';


  constructor(private http: HttpClient) {}

  getAll(): Observable<Department[]> {
    return this.http.get<Department[]>(this.baseUrl);
  }

  getById(id: number): Observable<Department> {
    return this.http.get<Department>(`${this.baseUrl}/${id}`);
  }

  create(Department: Department): Observable<Department> {
    return this.http.post<Department>(this.baseUrl, Department);
  }

  update(id: number, Department: Department): Observable<Department> {
    return this.http.put<Department>(`${this.baseUrl}/${id}`, Department);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
  
}
