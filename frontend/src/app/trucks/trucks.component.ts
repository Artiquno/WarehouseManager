import { Component, OnInit } from '@angular/core';

import { Truck } from './truck';
import { TrucksService } from '../services/trucks/trucks.service';
import { Page } from '../models/Page';

@Component({
  selector: 'app-trucks',
  templateUrl: './trucks.component.html',
  styleUrls: ['./trucks.component.css']
})
export class TrucksComponent implements OnInit {

  constructor(private trucksService: TrucksService) { }

  trucks: Truck[] = [];
  page: Page<Truck> | null = null;

  ngOnInit(): void {
    this.getTrucks();
  }

  getTrucks(): void {
    this.trucksService.getTrucks()
        .subscribe(page => {
          this.trucks = page.content
          this.page = page;
        });
  }

}
