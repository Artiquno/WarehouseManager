import { Component, OnInit } from '@angular/core';

import { Truck } from '../models/Truck';
import { TrucksService } from '../services/trucks/trucks.service';
import { Page } from '../models/Page';

@Component({
    selector: 'app-trucks',
    templateUrl: './trucks.component.html',
    styleUrls: ['./trucks.component.css']
})
export class TrucksComponent implements OnInit {
    trucks: Truck[] = [];
    page: Page<Truck> | null = null;
    
    pageNr: number = 0;
    pageSize: number = 10;

    constructor(private trucksService: TrucksService) { }

    ngOnInit(): void {
        this.getTrucks();
    }

    getTrucks(): void {
        this.trucksService.getTrucks(this.pageSize, this.pageNr)
            .subscribe(page => {
                this.trucks = page.content
                this.page = page;
            });
    }

    nextPage(): void {
        this.pageNr += 1;
        this.getTrucks();
    }

    prevPage(): void {
        this.pageNr -= 1;
        this.getTrucks();
    }
}
