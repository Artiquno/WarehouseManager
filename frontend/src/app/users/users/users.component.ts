import { Component, OnInit } from '@angular/core';
import { Page } from 'src/app/models/Page';
import { User } from 'src/app/models/UserInfo';
import { UsersService } from 'src/app/services/users/users.service';

@Component({
    selector: 'app-users',
    templateUrl: './users.component.html',
    styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

    users: User[] = [];
    page: Page<User> | null = null;

    pageNr: number = 0;
    pageSize: number = 10;

    constructor(private usersService: UsersService) { }

    ngOnInit(): void {
        this.getUsers();
    }

    getUsers(): void {
        this.usersService.getUsers(this.pageSize, this.pageNr)
            .subscribe(page => {
                this.users = page.content
                this.page = page;
            });
    }

    nextPage(): void {
        this.pageNr += 1;
        this.getUsers();
    }

    prevPage(): void {
        this.pageNr -= 1;
        this.getUsers();
    }
}
