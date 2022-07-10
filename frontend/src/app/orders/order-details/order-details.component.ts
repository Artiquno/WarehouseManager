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

    sc: any = {
        create: {
            action: "Create",
            api: null,
            role: ""
        },
        submit: {
            action: "Submit",
            api: "submit",
            role: "ROLE_CLIENT"
        },
        approve: {
            action: "Approve",
            api: "approve",
            role: "ROLE_WAREHOUSE_MANAGER"
        },
        decline: {
            action: "Decline",
            api: "decline",
            role: "ROLE_WAREHOUSE_MANAGER"
        },
        cancel: {
            action: "Cancel",
            api: "cancel",
            role: "ROLE_CLIENT"
        },
        fulfill: {
            action: "Fulfill",
            api: "fulfill",
            role: "ROLE_WAREHOUSE_MANAGER"
        }
    };

    transitions = {
        "CREATED": [
            this.sc.submit,
            this.sc.cancel
        ],
        "AWAITING_APPROVAL": [
            this.sc.approve,
            this.sc.decline,
            this.sc.cancel
        ],
        "APPROVED": [
            this.sc.cancel
        ],
        "DECLINED": [
            this.sc.submit,
            this.sc.cancel
        ],
        "UNDER_DELIVERY": [
            this.sc.fulfill
        ],
        "FULFILLED": [],
        "CANCELLED": []
    };

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

    changeStatus(api: string): void {
        this.ordersService.changeStatus(this.order.id, api)
            .subscribe(order => this.order = order);
    }
}
