import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { Constants } from './constants';
import { UserInfo as UserInfo } from './models/UserInfo';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private token: string | null = null;
  private userInfo: UserInfo | null = null;

  constructor(private http: HttpClient) {
    this.token = localStorage.getItem("authToken");
  }

  logIn(username: string, password: string): Observable<HttpResponse<UserInfo>> {
    let result = this.http.post<UserInfo>(Constants.BASE_URL + "/login", {
      "username": username,
      "password": password
    },
    { observe: "response" })
    .pipe(
      catchError(
        error => {
          console.error("Could not log in");
          return throwError(() => new Error("Could not log in"));
        }),
    );

    result.subscribe((response) => {
      this.userInfo = response.body;
      if(response.headers.has("Authorization"))
      {
        this.token = response.headers.get("Authorization");
        localStorage.setItem("authToken", this.token ?? "Bearer of the curse");
      }
    });

    return result;
  }

  getToken(): string | null {
    return this.token;
  }

  getUserInfo(): UserInfo | null {
    return this.userInfo;
  }

  isAuthenticated(): boolean {
    return this.token !== null && this.token.length > 1;
  }

  createDefaultUser(): Observable<void> {
    return this.http.post<void>(Constants.BASE_URL + "/users/create-default", {});
  }
}
