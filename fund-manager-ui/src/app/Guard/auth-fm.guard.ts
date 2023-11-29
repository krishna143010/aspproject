import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { UserService } from '../Service/user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthFMGuard implements CanActivate {
  constructor(private service:UserService,private route:Router){}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      console.log("IsLoogedIn() giving "+this.service.IsLoogedIn());
    if(this.service.IsUserFM()){
      
      return true;
    }else{
      this.service.clearToken();
      this.route.navigate(['login']);
      return false;
    }
  }
  
}
