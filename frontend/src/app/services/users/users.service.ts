import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Constants } from 'src/app/constants';
import { Page } from 'src/app/models/Page';
import { User } from 'src/app/models/UserInfo';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private http: HttpClient) { }

  getUsers(pageSize?: number, pageNr?: number): Observable<Page<User>> {
    return this.http.get<Page<User>>(Constants.BASE_URL + `/users?page=${pageNr}&size=${pageSize}`);
  }

  getUser(id: number): Observable<User> {
    return this.http.get<User>(Constants.BASE_URL + "/users/" + id);
  }

  updateUser(user: User): Observable<User> {
    return this.http.put<User>(Constants.BASE_URL + "/users", user);
  }

  createUser(user: User): Observable<User> {
    return this.http.post<User>(Constants.BASE_URL + "/users", user);
  }

  createDefaultUser(): Observable<string> {
    return this.http.post(Constants.BASE_URL + "/users/create-default", {}, {
      responseType: "text"
    });
  }
}
