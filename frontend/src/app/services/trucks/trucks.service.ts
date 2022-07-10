import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import { Truck } from '../../models/Truck';
import { Constants } from '../../constants';
import { Page } from '../../models/Page';

@Injectable({
  providedIn: 'root'
})
export class TrucksService {
  constructor(private http: HttpClient) { }

  getTrucks(pageSize?: number, pageNr?: number): Observable<Page<Truck>> {
    return this.http.get<Page<Truck>>(Constants.BASE_URL + `/trucks?page=${pageNr}&size=${pageSize}`);
  }

  getTruck(id: number): Observable<Truck> {
    return this.http.get<Truck>(Constants.BASE_URL + "/trucks/" + id);
  }

  updateTruck(truck: Truck): Observable<Truck> {
    return this.http.put<Truck>(Constants.BASE_URL + "/trucks", truck);
  }

  createTruck(truck: Truck): Observable<Truck> {
    return this.http.post<Truck>(Constants.BASE_URL + "/trucks", truck);
  }
}
