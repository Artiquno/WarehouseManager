import { Item } from "./Item";

export interface OrderedItem {
    item: Item;
    requestedQuantity: number;
}