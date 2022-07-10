import { Component, OnInit } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { Schedule } from 'src/app/models/Schedule';
import { Truck } from 'src/app/models/Truck';
import { SchedulesService } from 'src/app/services/schedules/schedules.service';
import { TrucksService } from 'src/app/services/trucks/trucks.service';

@Component({
    selector: 'app-schedule-delivery',
    templateUrl: './schedule-delivery.component.html',
    styleUrls: ['./schedule-delivery.component.css']
})
export class ScheduleDeliveryComponent implements OnInit {
    schedule: Schedule = {
        deliveryDate: new Date(),
        trucks: []
    };

    trucks: Truck[] = [];

    deliveryResponse: string = "";

    constructor(private trucksService: TrucksService,
                private schedulesService: SchedulesService) { }

    ngOnInit(): void {
        this.getTrucks();
    }

    getTrucks(): void {
        this.trucksService.getTrucks()
            .subscribe(trucks => this.trucks = trucks.content);
    }

    save(): void {
        this.schedulesService.createSchedule(this.schedule)
        .pipe(
            catchError((error, caught) => {
                this.deliveryResponse = error.error.message;
                return throwError(() => new Error("Could not schedule delivery"));
            })
        )
        .subscribe(response => this.deliveryResponse = response.result);
  }
}
