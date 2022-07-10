import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Constants } from 'src/app/constants';
import { Schedule } from 'src/app/models/Schedule';

@Injectable({
  providedIn: 'root'
})
export class DeliveriesService {
  constructor(private http: HttpClient) { }

  createSchedule(schedule: Schedule): Observable<any> {
    return this.http.post<any>(Constants.BASE_URL + "/orders/schedule-delivery", schedule);
  }
}
