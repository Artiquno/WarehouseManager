import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/models/UserInfo';
import { UsersService } from 'src/app/services/users/users.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {
  isUpdate: boolean = false;

  roles: String[] = [
      "ROLE_SYSTEM_ADMIN",
      "ROLE_WAREHOUSE_MANAGER",
      "ROLE_CLIENT"
  ];

  user: User = {
    id: 0,
    username: "",
    email: "",
    roles: []
  };

  constructor(
      private router: Router,
      private route: ActivatedRoute,
      private usersService: UsersService) { }

  ngOnInit(): void {
      if (this.route.snapshot.paramMap.has("id")) {
          this.isUpdate = true;
          this.user.id = Number(this.route.snapshot.paramMap.get("id"));
          this.getUserData();
      }
  }

  getUserData(): void {
      this.usersService.getUser(this.user.id)
          .subscribe(user => this.user = user);
  }

  saveUser(): void {
      if(this.isUpdate) {
          this.usersService.updateUser(this.user)
              .subscribe(user => this.router.navigate(["/users/" + this.user.id]));
      } else {
          this.usersService.createUser(this.user)
              .subscribe(user => this.router.navigate(["/users"]));
      }
  }
}
