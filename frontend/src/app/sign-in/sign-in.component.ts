import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {
  username: string = "";
  password: string = "";

  constructor(private authService: AuthenticationService,
    private router: Router) { }

  ngOnInit(): void {
  }

  doSignIn(): void {
    this.authService.logIn(this.username, this.password)
      .subscribe(response => {
        console.log("I'm in!");
        this.router.navigate(['/']);
      });
  }
}
