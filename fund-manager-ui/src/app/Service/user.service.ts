import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BYPASS_LOG } from './token-interceptor.service';
import { Buffer } from 'buffer';
@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }
  ProceedLogin(inputdata: any) {
    console.log("Input data:"+JSON.stringify(inputdata));
    return this.http.post('http://localhost:8081/user-service/authenticate', inputdata,{ context: new HttpContext().set(BYPASS_LOG, true) ,responseType: 'text'});
  }
  IsLoogedIn() {
    //this.GetRole();
    return localStorage.getItem('token') != null;
  }

  GetToken() {

    return localStorage.getItem('token') != null ? localStorage.getItem('token') : '';

  }

  Registeration(inputdata: any) {
    const updatedVariable: any = {
      ...inputdata,
      roles: "ROLE_FM",
    };
    console.log("Input data"+JSON.stringify(updatedVariable));
    return this.http.post('http://localhost:8081/user-service/addUser', updatedVariable,{ context: new HttpContext().set(BYPASS_LOG, true) ,responseType: 'text'});
  }

  GetRole() {
    var token = localStorage.getItem('token');
    if (token != null) {
      var extractdata = JSON.parse(Buffer.from(token.split('.')[1], 'base64').toString());
      return extractdata.role;
    }else{
      return '';
    }

  }
}
