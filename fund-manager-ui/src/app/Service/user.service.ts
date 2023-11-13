import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }
  ProceedLogin(inputdata: any) {
    inputdata=inputdata
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
    const updatedVariable: any = {
      ...inputdata,
      roles: "ROLE_FM",
    };
    console.log("Input data"+JSON.stringify(updatedVariable));
    return this.http.post('http://localhost:8081/user-service/addUser', updatedVariable,{responseType: 'text'});
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
