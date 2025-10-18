import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { isPlatformBrowser } from '@angular/common';
import { Observable } from 'rxjs';
import { deptHead } from '../model/deptHead.model';

@Injectable({
  providedIn: 'root'
})
export class DeptHeadService {

  private baseUrl = environment.apiUrl + '/deptHead';

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) { }

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

  getAll(): Observable<deptHead[]> {
      let headers = new HttpHeaders();
  
      if (isPlatformBrowser(this.platformId)) {
        const token = localStorage.getItem('authToken');
        if (token) {
          headers = headers.set('Authorization', 'Bearer ' + token);
        }
      }
  
      return this.http.get<deptHead[]>(`${this.baseUrl}`, { headers })
    }


}
