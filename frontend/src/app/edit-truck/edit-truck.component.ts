import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TrucksService } from '../trucks.service';
import { Truck } from '../trucks/truck';

@Component({
    selector: 'app-edit-truck',
    templateUrl: './edit-truck.component.html',
    styleUrls: ['./edit-truck.component.css']
})
export class EditTruckComponent implements OnInit {
    isUpdate: boolean = false;

    truck: Truck = {
        id: 0,
        chassis: "",
        licensePlate: ""
    };

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private trucksService: TrucksService) { }

    ngOnInit(): void {
        if (this.route.snapshot.paramMap.has("id")) {
            this.isUpdate = true;
            this.truck.id = Number(this.route.snapshot.paramMap.get("id"));
            this.getTruckData();
        }
    }

    getTruckData(): void {
        this.trucksService.getTruck(this.truck.id)
            .subscribe(truck => this.truck = truck);
    }

    saveTruck(): void {
        if(this.isUpdate) {
            this.trucksService.updateTruck(this.truck)
                .subscribe(truck => this.router.navigate(["/trucks/" + this.truck.id]));
        } else {
            this.trucksService.createTruck(this.truck)
                .subscribe(truck => this.router.navigate(["/trucks"]));
        }
    }
}
