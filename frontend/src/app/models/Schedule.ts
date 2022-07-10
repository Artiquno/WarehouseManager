import { Truck } from "./Truck";

export interface Schedule {
    deliveryDate: Date;
    trucks: Truck[];
}