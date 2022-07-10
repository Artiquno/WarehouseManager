import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/models/UserInfo';
import { UsersService } from 'src/app/services/users/users.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private usersService: UsersService) { }

  user: User = {
    id: 0,
    username: "",
    email: "",
    roles: []
  };

  ngOnInit(): void {
    this.refreshUser();
  }
  
  refreshUser(): void {
    let id: number = Number(this.route.snapshot.paramMap.get("id"));
    this.usersService.getUser(id)
        .subscribe(user => this.user = user);
  }

}
