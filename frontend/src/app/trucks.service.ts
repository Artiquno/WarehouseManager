import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpClient } from '@angular/common/http';

import { Truck } from './trucks/truck';
import { Constants } from './constants';
import { Page } from './models/Page';

@Injectable({
  providedIn: 'root'
})
export class TrucksService {
  trucks: Truck[] = [
    {
      id: 1,
      chassis: "Hello",
      licensePlate: "There"
    },
    {
      id: 2,
      chassis: "Meh",
      licensePlate: "Eh"
    },
    {
      id: 3,
      chassis: "Bah",
      licensePlate: "Pff"
    },
    {
      id: 4,
      chassis: "Ugh",
      licensePlate: "Hmm"
    }
  ];

  constructor(private http: HttpClient) { }

  getTrucks(): Observable<Page<Truck>> {
    return this.http.get<Page<Truck>>(Constants.BASE_URL + "/trucks");
  }

  getTruck(id: number): Observable<Truck> {
    return this.http.get<Truck>(Constants.BASE_URL + "/trucks/" + id);
  }

  updateTruck(truck: Truck): Observable<Truck> {
    return this.http.put<Truck>(Constants.BASE_URL + "/trucks", truck, {
      headers: {
        "Content-Type": "application/json"
      }
    });
  }

  createTruck(truck: Truck): Observable<Truck> {
    return this.http.post<Truck>(Constants.BASE_URL + "/trucks", truck);
  }
}
