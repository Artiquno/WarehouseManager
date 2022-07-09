import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { TruckDetailsComponent } from './truck-details/truck-details.component';
import { TrucksComponent } from './trucks/trucks.component';

const routes: Routes = [
  { path: "", component: DashboardComponent },
  { path: "trucks", component: TrucksComponent },
  { path: "trucks/:id", component: TruckDetailsComponent },
  { path: "login", component: SignInComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
