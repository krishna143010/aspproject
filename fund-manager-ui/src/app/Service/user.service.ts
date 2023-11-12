import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }
  ProceedLogin(inputdata: any) {
    return this.http.post('http://localhost:8081/user-service/authenticate', inputdata,{responseType: 'text'});
  }
  IsLoogedIn() {
    //this.GetRole();
    return localStorage.getItem('token') != null;
  }

  GetToken() {

    return localStorage.getItem('token') != null ? localStorage.getItem('token') : '';

  }

  Registeration(inputdata: any) {
    return this.http.post('https://localhost:44308/User/Register', inputdata);
  }

  GetRole() { //TODO
    var token = localStorage.getItem('token');
    if (token != null) {
      var extractdata = JSON.parse(Buffer.from(token.split('.')[1], 'base64').toString());
      console.log("extractdata"+JSON.stringify(extractdata))
      return extractdata.role;
    }else{
      return '';
    }

  }
}
