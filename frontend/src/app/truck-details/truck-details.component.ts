import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { TrucksService } from '../services/trucks/trucks.service';
import { Truck } from '../models/Truck';

@Component({
  selector: 'app-truck-details',
  templateUrl: './truck-details.component.html',
  styleUrls: ['./truck-details.component.css']
})
export class TruckDetailsComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private trucksService: TrucksService) { }

  truck: Truck = {
    id: 0,
    chassis: "",
    licensePlate: ""
  };

  ngOnInit(): void {
    this.refreshTruck();
  }
  
  refreshTruck(): void {
    let id: number = Number(this.route.snapshot.paramMap.get("id"));
    this.trucksService.getTruck(id)
        .subscribe(truck => this.truck = truck);
  }
}
