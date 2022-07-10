import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Item } from 'src/app/models/Item';
import { Order } from 'src/app/models/Order';
import { OrderedItem } from 'src/app/models/OrderedItem';
import { OrderStatus } from 'src/app/models/OrderStatus';
import { ItemsService } from 'src/app/services/items/items.service';
import { OrdersService } from 'src/app/services/orders/orders.service';

@Component({
    selector: 'app-edit-order',
    templateUrl: './edit-order.component.html',
    styleUrls: ['./edit-order.component.css']
})
export class EditOrderComponent implements OnInit {
    isUpdate: boolean = false;

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

    items: OrderedItem[] = [];

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private ordersService: OrdersService,
        private itemsService: ItemsService) { }

    ngOnInit(): void {
        if (this.route.snapshot.paramMap.has("id")) {
            this.isUpdate = true;
            this.order.id = Number(this.route.snapshot.paramMap.get("id"));
            this.getOrderData();
        }
        this.getItems();
    }

    getOrderData(): void {
        this.ordersService.getOrder(this.order.id)
            .subscribe(order => this.order = order);
    }

    getItems(): void {
        this.itemsService.getItems()
            .subscribe(page => this.items = page.content.map(item => {
                return {
                    item: item,
                    requestedQuantity: 0
                }
            }));
    }

    saveOrder(): void {
        if (this.isUpdate) {
            this.ordersService.updateOrder(this.order)
                .subscribe(order => this.router.navigate(["/orders/" + this.order.id]));
        } else {
            this.ordersService.createOrder(this.order)
                .subscribe(order => this.router.navigate(["/orders"]));
        }
    }
}
