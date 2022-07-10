import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Order } from 'src/app/models/Order';
import { OrderStatus } from 'src/app/models/OrderStatus';
import { OrdersService } from 'src/app/services/orders/orders.service';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.css']
})
export class OrderDetailsComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private ordersService: OrdersService) { }

  order: Order = {
    id: 0,
    submittedDate: new Date(),
    deadlineDate: new Date(),
    owner: {
      id: 0,
      email: "",
      username: "",
      roles: [],
    },
    items: [],
    status: OrderStatus.Created
  };

  ngOnInit(): void {
    this.refreshOrder();
  }
  
  refreshOrder(): void {
    let id: number = Number(this.route.snapshot.paramMap.get("id"));
    this.ordersService.getOrder(id)
        .subscribe(order => this.order = order);
  }

}
