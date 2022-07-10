import { Item } from "./Item";
import { OrderedItem } from "./OrderedItem";
import { OrderStatus } from "./OrderStatus";
import { User } from "./UserInfo";

export interface Order {
    id: number;
    submittedDate: Date;
    deadlineDate: Date;
    owner: User;
    status: OrderStatus;
    items?: OrderedItem[];
}