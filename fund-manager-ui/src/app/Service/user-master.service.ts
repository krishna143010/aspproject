import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FundManagerModel } from '../Model/FundManagerModel';
import { UserModel } from '../Model/UserModel';

@Injectable({
  providedIn: 'root'
})
export class UserMasterService {

  constructor(private http: HttpClient) { }
  apiurl = 'http://localhost:8081';

  GetAllUser():Observable<UserModel[]> {
    return this.http.get<UserModel[]>(this.apiurl+'/user-service/Users');
  }

  GetUserbyId(UserId: any) {
    return this.http.get(this.apiurl + '/user-service/Users/' + UserId);
  }


  UpdateUser(username: any,activeStatus:any) {
    const updatedVariable: any = {
      username: username,
      enableStatus: activeStatus
    };
    console.log("Input data:"+JSON.stringify(updatedVariable));
    return this.http.post(this.apiurl + '/user-service/changeUserLoginStatus', updatedVariable,{responseType: 'text'});
  }



}
