import { Component, OnInit } from '@angular/core';
import { Item } from 'src/app/models/Item';
import { Page } from 'src/app/models/Page';
import { ItemsService } from 'src/app/services/items/items.service';

@Component({
    selector: 'app-items',
    templateUrl: './items.component.html',
    styleUrls: ['./items.component.css']
})
export class ItemsComponent implements OnInit {
    
    items: Item[] = [];
    page: Page<Item> | null = null;
    
    pageNr: number = 0;
    pageSize: number = 10;
    
    constructor(private itemsService: ItemsService) { }

    ngOnInit(): void {
        this.getItems();
    }

    getItems(): void {
        this.itemsService.getItems(this.pageSize, this.pageNr)
            .subscribe(page => {
                this.items = page.content
                this.page = page;
            });
    }

    nextPage(): void {
        this.pageNr += 1;
        this.getItems();
    }

    prevPage(): void {
        this.pageNr -= 1;
        this.getItems();
    }
}
