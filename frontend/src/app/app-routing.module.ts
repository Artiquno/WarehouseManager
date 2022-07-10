import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ScheduleDeliveryComponent } from './deliveries/schedule-delivery/schedule-delivery.component';
import { EditTruckComponent } from './edit-truck/edit-truck.component';
import { EditItemComponent } from './items/edit-item/edit-item.component';
import { ItemDetailsComponent } from './items/item-details/item-details.component';
import { ItemsComponent } from './items/items/items.component';
import { EditOrderComponent } from './orders/edit-order/edit-order.component';
import { OrderDetailsComponent } from './orders/order-details/order-details.component';
import { OrdersComponent } from './orders/orders/orders.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { TruckDetailsComponent } from './truck-details/truck-details.component';
import { TrucksComponent } from './trucks/trucks.component';
import { EditUserComponent } from './users/edit-user/edit-user.component';
import { UserDetailsComponent } from './users/user-details/user-details.component';
import { UsersComponent } from './users/users/users.component';

const routes: Routes = [
  { path: "", component: DashboardComponent },

  { path: "trucks", component: TrucksComponent },
  { path: "trucks/create", component: EditTruckComponent },
  { path: "trucks/:id", component: TruckDetailsComponent },
  { path: "trucks/:id/edit", component: EditTruckComponent },

  { path: "users", component: UsersComponent },
  { path: "users/create", component: EditUserComponent },
  { path: "users/:id", component: UserDetailsComponent },
  { path: "users/:id/edit", component: EditUserComponent },

  { path: "items", component: ItemsComponent },
  { path: "items/create", component: EditItemComponent },
  { path: "items/:id", component: ItemDetailsComponent },
  { path: "items/:id/edit", component: EditItemComponent },

  { path: "orders", component: OrdersComponent },
  { path: "orders/create", component: EditOrderComponent },
  { path: "orders/:id", component: OrderDetailsComponent },
  { path: "orders/:id/edit", component: EditOrderComponent },

  { path: "schedule", component: ScheduleDeliveryComponent},

  { path: "login", component: SignInComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
