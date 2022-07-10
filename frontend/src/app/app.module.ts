import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgSelectModule } from "@ng-select/ng-select"

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TrucksComponent } from './trucks/trucks.component';
import { TruckDetailsComponent } from './truck-details/truck-details.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AuthorizationInterceptor } from './authorization.interceptor';
import { SignInComponent } from './sign-in/sign-in.component';
import { EditTruckComponent } from './edit-truck/edit-truck.component';
import { OrdersComponent } from './orders/orders/orders.component';
import { OrderDetailsComponent } from './orders/order-details/order-details.component';
import { EditOrderComponent } from './orders/edit-order/edit-order.component';
import { UsersComponent } from './users/users/users.component';
import { UserDetailsComponent } from './users/user-details/user-details.component';
import { EditUserComponent } from './users/edit-user/edit-user.component';
import { ItemsComponent } from './items/items/items.component';
import { EditItemComponent } from './items/edit-item/edit-item.component';
import { ItemDetailsComponent } from './items/item-details/item-details.component';
import { ScheduleDeliveryComponent } from './deliveries/schedule-delivery/schedule-delivery.component';

@NgModule({
  declarations: [
    AppComponent,
    TrucksComponent,
    TruckDetailsComponent,
    DashboardComponent,
    SignInComponent,
    EditTruckComponent,
    OrdersComponent,
    OrderDetailsComponent,
    EditOrderComponent,
    UsersComponent,
    UserDetailsComponent,
    EditUserComponent,
    ItemsComponent,
    EditItemComponent,
    ItemDetailsComponent,
    ScheduleDeliveryComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgSelectModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthorizationInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
