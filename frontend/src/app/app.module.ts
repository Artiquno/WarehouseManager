import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TrucksComponent } from './trucks/trucks.component';
import { TruckDetailsComponent } from './truck-details/truck-details.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AuthorizationInterceptor } from './authorization.interceptor';
import { SignInComponent } from './sign-in/sign-in.component';
import { EditTruckComponent } from './edit-truck/edit-truck.component';

@NgModule({
  declarations: [
    AppComponent,
    TrucksComponent,
    TruckDetailsComponent,
    DashboardComponent,
    SignInComponent,
    EditTruckComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthorizationInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
