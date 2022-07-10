import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Constants } from 'src/app/constants';
import { Order } from 'src/app/models/Order';
import { Page } from 'src/app/models/Page';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {

  constructor(private http: HttpClient) { }

  getOrders(filter: string | null): Observable<Page<Order>> {
    const appended = filter !== null ? ("?status=" + filter) : "";
    return this.http.get<Page<Order>>(Constants.BASE_URL + "/orders" + appended);
  }

  getOrder(id: number): Observable<Order> {
    return this.http.get<Order>(Constants.BASE_URL + "/orders/" + id);
  }

  updateOrder(order: Order): Observable<Order> {
    return this.http.put<Order>(Constants.BASE_URL + "/orders", order);
  }

  createOrder(order: Order): Observable<Order> {
    return this.http.post<Order>(Constants.BASE_URL + "/orders", order);
  }

  changeStatus(orderId: number, api: string): Observable<Order> {
    return this.http.put<Order>(`${Constants.BASE_URL}/orders/${orderId}/${api}`, {});
  }
}
