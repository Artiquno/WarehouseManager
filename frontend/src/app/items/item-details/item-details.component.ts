import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Item } from 'src/app/models/Item';
import { ItemsService } from 'src/app/services/items/items.service';

@Component({
  selector: 'app-item-details',
  templateUrl: './item-details.component.html',
  styleUrls: ['./item-details.component.css']
})
export class ItemDetailsComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private itemsService: ItemsService) { }

  item: Item = {
    id: 0,
    name: "",
    quantityInStock: 0,
    unitPrice: 0.0
  };

  ngOnInit(): void {
    this.refreshItem();
  }
  
  refreshItem(): void {
    let id: number = Number(this.route.snapshot.paramMap.get("id"));
    this.itemsService.getItem(id)
        .subscribe(item => this.item = item);
  }

}
