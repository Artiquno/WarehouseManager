import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Constants } from 'src/app/constants';
import { Item } from 'src/app/models/Item';
import { Page } from 'src/app/models/Page';

@Injectable({
  providedIn: 'root'
})
export class ItemsService {

  constructor(private http: HttpClient) { }

  getItems(pageSize?: number, pageNr?: number): Observable<Page<Item>> {
    return this.http.get<Page<Item>>(Constants.BASE_URL + `/items?page=${pageNr}&size=${pageSize}`);
  }

  getItem(id: number): Observable<Item> {
    return this.http.get<Item>(Constants.BASE_URL + "/items/" + id);
  }

  updateItem(item: Item): Observable<Item> {
    return this.http.put<Item>(Constants.BASE_URL + "/items", item);
  }

  createItem(item: Item): Observable<Item> {
    return this.http.post<Item>(Constants.BASE_URL + "/items", item);
  }
}
