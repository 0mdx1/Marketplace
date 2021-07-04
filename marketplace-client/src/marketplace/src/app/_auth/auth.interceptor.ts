import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthInterceptor implements HttpInterceptor {
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token = req.headers.get('Authorization');
    if (token) {
      localStorage.setItem('token', token);
    }
    return next.handle(req);
  }

  private addAuthenticationToken(request: HttpRequest<any>): HttpRequest<any> {
    const token = localStorage.getItem('token');
    if (!token) {
      return request;
    }
    return request.clone({
      headers: request.headers.set('Authorization', 'Bearer ' + token),
    });
  }
}
