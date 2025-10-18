import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Holiday } from '../model/holiday.model';

@Injectable({
  providedIn: 'root'
})
export class HolidayService {

  private baseUrl = environment.apiUrl+'/holiday'; // replace with your backend URL

  constructor(private http: HttpClient) { }

  // Get all holidays
  getAllHolidays(): Observable<Holiday[]> {
    return this.http.get<Holiday[]>(this.baseUrl);
  }

  // Add a holiday
  addHoliday(date: string, description: string): Observable<Holiday> {
    const params = { date, description };
    return this.http.post<Holiday>(this.baseUrl, null, { params });
  }

  // Delete a holiday
  deleteHoliday(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
  
}
