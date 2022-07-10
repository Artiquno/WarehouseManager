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

  constructor(private itemsService: ItemsService) { }

  items: Item[] = [];
  page: Page<Item> | null = null;

  ngOnInit(): void {
    this.getItems();
  }

  getItems(): void {
    this.itemsService.getItems()
        .subscribe(page => {
          this.items = page.content
          this.page = page;
        });
  }

}
