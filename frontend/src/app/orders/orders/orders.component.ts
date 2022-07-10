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

  statusFilter: string | null = null;

  pageNr: number = 0;
  pageSize: number = 10;

  constructor(private ordersService: OrdersService) {}

  ngOnInit(): void {
    this.getOrders();
  }

  getOrders(): void {
    this.ordersService.getOrders(this.statusFilter, this.pageSize, this.pageNr)
        .subscribe(page => {
          this.orders = page.content
          this.page = page;
        });
  }

  nextPage(): void {
    this.pageNr += 1;
    this.getOrders();
  }

  prevPage(): void {
    this.pageNr -= 1;
    this.getOrders();
  }
}
