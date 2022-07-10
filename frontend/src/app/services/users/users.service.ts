import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Constants } from 'src/app/constants';
import { Page } from 'src/app/models/Page';
import { UserInfo } from 'src/app/models/UserInfo';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private http: HttpClient) { }

  getUsers(): Observable<Page<UserInfo>> {
    return this.http.get<Page<UserInfo>>(Constants.BASE_URL + "/users");
  }

  getUser(id: number): Observable<UserInfo> {
    return this.http.get<UserInfo>(Constants.BASE_URL + "/users/" + id);
  }

  updateUser(user: UserInfo): Observable<UserInfo> {
    return this.http.put<UserInfo>(Constants.BASE_URL + "/users", user);
  }

  createUser(user: UserInfo): Observable<UserInfo> {
    return this.http.post<UserInfo>(Constants.BASE_URL + "/users", user);
  }

  createDefaultUser(): Observable<string> {
    return this.http.post(Constants.BASE_URL + "/users/create-default", {}, {
      responseType: "text"
    });
  }
}
