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

  constructor(private usersService: UsersService) { }

  users: User[] = [];
  page: Page<User> | null = null;

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers(): void {
    this.usersService.getUsers()
        .subscribe(page => {
          this.users = page.content
          this.page = page;
        });
  }

}
