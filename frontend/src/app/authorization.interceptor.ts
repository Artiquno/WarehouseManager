import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationService } from './authentication.service';

@Injectable()
export class AuthorizationInterceptor implements HttpInterceptor {

  constructor(private authService: AuthenticationService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    let updatedRequest = request;
    if(this.authService.isAuthenticated())
    {
      updatedRequest = request.clone({
        headers: request.headers.append("Authorization", this.authService.getToken() ?? "")
      });
    }
    return next.handle(updatedRequest);
  }
}
