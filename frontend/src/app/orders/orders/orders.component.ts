import { Component, OnInit } from '@angular/core';
import { Order } from 'src/app/models/Order';
import { Page } from 'src/app/models/Page';
import { OrdersService } from 'src/app/services/orders/orders.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  orders: Order[] = [];
  page: Page<Order> | null = null;

  constructor(private ordersService: OrdersService) {}

  ngOnInit(): void {
    this.getOrders();
  }

  getOrders(): void {
    this.ordersService.getOrders()
        .subscribe(page => {
          this.orders = page.content
          this.page = page;
        });
  }

}
