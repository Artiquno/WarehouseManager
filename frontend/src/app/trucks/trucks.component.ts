import { Component, OnInit } from '@angular/core';

import { Truck } from './truck';
import { TrucksService } from '../trucks.service';

@Component({
  selector: 'app-trucks',
  templateUrl: './trucks.component.html',
  styleUrls: ['./trucks.component.css']
})
export class TrucksComponent implements OnInit {

  constructor(private trucksService: TrucksService) { }

  trucks: Truck[] = [];

  ngOnInit(): void {
    this.getHeroes();
  }

  getHeroes(): void {
    this.trucksService.getTrucks()
        .subscribe(trucks => this.trucks = trucks);
  }

}
