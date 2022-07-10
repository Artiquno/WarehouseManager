import { Item } from "./Item";
import { OrderStatus } from "./OrderStatus";
import { UserInfo } from "./UserInfo";

export interface Order {
    id: number;
    submittedDate: Date;
    deadlineDate: Date;
    owner: UserInfo;
    status: OrderStatus;
    items?: Item[];
}