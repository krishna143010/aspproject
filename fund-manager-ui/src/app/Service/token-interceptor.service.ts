import { HttpContextToken, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class TokenInterceptorService implements HttpInterceptor {

  constructor() { }
  //token = localStorage.getItem('token')?localStorage.getItem('token'):null;
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (req.context.get(BYPASS_LOG) === true)
      return next.handle(req);
    let  jwttoken=req.clone({
        setHeaders: {
          Authorization: 'Bearer ' + localStorage.getItem('token')
        }
      });
    
    
    return next.handle(jwttoken)
  }
}


export const BYPASS_LOG = new HttpContextToken<boolean>(() => false);