import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Item } from 'src/app/models/Item';
import { ItemsService } from 'src/app/services/items/items.service';

@Component({
    selector: 'app-edit-item',
    templateUrl: './edit-item.component.html',
    styleUrls: ['./edit-item.component.css']
})
export class EditItemComponent implements OnInit {

    isUpdate: boolean = false;

    item: Item = {
        id: 0,
        name: "",
        quantityInStock: 0,
        unitPrice: 0.0
    };

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private itemsService: ItemsService) { }

    ngOnInit(): void {
        if (this.route.snapshot.paramMap.has("id")) {
            this.isUpdate = true;
            this.item.id = Number(this.route.snapshot.paramMap.get("id"));
            this.getItemData();
        }
    }

    getItemData(): void {
        this.itemsService.getItem(this.item.id)
            .subscribe(item => this.item = item);
    }

    saveItem(): void {
        if (this.isUpdate) {
            this.itemsService.updateItem(this.item)
                .subscribe(item => this.router.navigate(["/items/" + this.item.id]));
        } else {
            this.itemsService.createItem(this.item)
                .subscribe(item => this.router.navigate(["/items"]));
        }
    }

}
