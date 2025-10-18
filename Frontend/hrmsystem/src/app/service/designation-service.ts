import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Designation } from '../model/Designation.model';

@Injectable({
  providedIn: 'root'
})
export class DesignationService {

   private baseUrl = environment.apiUrl + '/designation';


  constructor(private http: HttpClient) { }

  getAll(): Observable<Designation[]> {
    return this.http.get<Designation[]>(this.baseUrl);
  }

  getById(id: number): Observable<Designation> {
    return this.http.get<Designation>(`${this.baseUrl}/${id}`);
  }
  getByDepartmentId(deptId: number): Observable<Designation[]> {
    return this.http.get<Designation[]>(`${this.baseUrl}/by-department/${deptId}`);
  }

  create(Designation: Designation): Observable<Designation> {
    return this.http.post<Designation>(this.baseUrl, Designation);
  }

  update(id: number, Designation: Designation): Observable<Designation> {
    return this.http.put<Designation>(`${this.baseUrl}/${id}`, Designation);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
  
}
