import { HttpClient, HttpContext, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BYPASS_LOG } from './token-interceptor.service';
import { Buffer } from 'buffer';
import { FundManagerModel } from '../Model/FundManagerModel';
import { ClientModel } from '../Model/ClinetModel';
import { Observable } from 'rxjs';
import { AccountModel } from '../Model/AccountModel';
import { TransactionModel } from '../Model/TransactionModel';
import { JwtHelperService,JWT_OPTIONS } from '@auth0/angular-jwt';
import { TransactionsSummary } from '../Model/TransactionsSummary';
import { DatePipe } from '@angular/common';
import { StockData } from '../Model/StockData';
@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient,private datepipe: DatePipe) { }
  private jwtHelper = new JwtHelperService();
  //private datePipe!:DatePipe;
  ProceedLogin(inputdata: any) {
    console.log("Input data:"+JSON.stringify(inputdata));
    return this.http.post('http://localhost:8081/user-service/authenticate', inputdata,{ context: new HttpContext().set(BYPASS_LOG, true) ,responseType: 'text', observe:'response'});
  }
  IsLoogedIn() {
    //this.GetRole();
    let existingToken=localStorage.getItem('token');
    if(existingToken!=null){
      if (this.jwtHelper.isTokenExpired(existingToken)) {
        return false;
      } else {
        return true;
      }
    }else{
      return false;
    }
  }
  IsUserFM() {
    //this.GetRole();
    var token = localStorage.getItem('token');
    if (token != null && this.IsLoogedIn()) {
      var extractdata = JSON.parse(Buffer.from(token.split('.')[1], 'base64').toString());
      if(extractdata.role=='ROLE_FM'){
        return true;
      }else{
        return false;
      }
    }else{
      return false;
    }
  }
  IsUserClient() {
    //this.GetRole();
    var token = localStorage.getItem('token');
    if (token != null && this.IsLoogedIn()) {
      var extractdata = JSON.parse(Buffer.from(token.split('.')[1], 'base64').toString());
      if(extractdata.role=='ROLE_CLIENT'){
        return true;
      }else{
        return false;
      }
    }else{
      return false;
    }
  }
  IsUserADMIN() {
    //this.GetRole();
    var token = localStorage.getItem('token');
    if (token != null && this.IsLoogedIn()) {
      var extractdata = JSON.parse(Buffer.from(token.split('.')[1], 'base64').toString());
      if(extractdata.role=='ROLE_ADMIN'){
        return true;
      }else{
        return false;
      }
    }else{
      return false;
    }
  }

  GetToken() {

    return localStorage.getItem('token') != null ? localStorage.getItem('token') : '';

  }
  GetAllTransactions():Observable<TransactionModel[]> {
    return this.http.get<TransactionModel[]>('http://localhost:8081/transaction/Transactions');
  }
  GetFMSummary():Observable<TransactionsSummary> {
    return this.http.get<TransactionsSummary>('http://localhost:8081/transaction/Transactions/transactionSummary/'+this.getFMId());
  }

  Registeration(inputdata: any) {
    const updatedVariable: any = {
      ...inputdata,
      roles: "ROLE_FM",
    };
    console.log("Input data"+JSON.stringify(updatedVariable));
    return this.http.post('http://localhost:8081/user-service/addUser', updatedVariable,{ context: new HttpContext().set(BYPASS_LOG, true) ,responseType: 'json'});
  }
  saveClient(inputdata: any) {
    const params = new HttpParams().set('clientEmail', inputdata.email);
    const updatedVariable: any = {
      ...inputdata,
      fundManager: this.GetId(),
    };
    
    console.log("Input data"+JSON.stringify(updatedVariable));
    return this.http.post('http://localhost:8081/accounts/saveClients', updatedVariable,{ params,responseType: 'text'});
  }
  
  GetAllStocks():Observable<StockData> {
    return this.http.get<StockData>('/api/indices/constituents/.NSEI',{ context: new HttpContext().set(BYPASS_LOG, true)});
  }
  setInterest(inputdata: any) {
    
    console.log("Account ID is"+inputdata.accountId);
    let dateFormated=this.datepipe.transform(new Date(inputdata.startDate), 'MM-dd-yyyy')?.toString();
    const params = new HttpParams().set('accountId', inputdata.accountId).set('startDate', dateFormated?dateFormated:'').set('interestRate', inputdata.interestRate);
    //params.set('accountId', inputdata.accountId);
    // params.set('startDate', inputdata.startDate);
    // params.set('interestRate', inputdata.interestRate);
    return this.http.post('http://localhost:8081/accounts/addInterest', params,{ responseType: 'text'});
  }
  settleInterest(inputdata: any) {
    
    console.log("Account ID is"+inputdata.accountId);
    let dateFormated=this.datepipe.transform(new Date(inputdata.startDate), 'MM-dd-yyyy')?.toString();
    const params = new HttpParams().set('accountId', inputdata.accountId).set('settleDate', dateFormated?dateFormated:'');
    //params.set('accountId', inputdata.accountId);
    // params.set('startDate', inputdata.startDate);
    // params.set('interestRate', inputdata.interestRate);
    return this.http.post('http://localhost:8081/accounts/settleInterest', params,{ responseType: 'text'});
  }
  changePassword(inputdata: any) {
    const params = new HttpParams().set('newPassword', inputdata);
    return this.http.post('http://localhost:8081/user-service/changePassword',params,{ responseType: 'text'});
  }
  resetPassword(inputdata: any) {
    const params = new HttpParams().set('email', inputdata.email);
    return this.http.post('http://localhost:8081/user-service/resetPassword',params,{ context: new HttpContext().set(BYPASS_LOG, true) ,responseType: 'text'});
  }
  saveAccount(inputdata: any) {
    console.log("Input data for saving account:"+JSON.stringify(inputdata));
    return this.http.post('http://localhost:8081/accounts/saveAccounts', inputdata,{ responseType: 'json'});
  }
  saveTransaction(inputdata: any) {
    console.log("Input data for saving account:"+JSON.stringify(inputdata));
    return this.http.post('http://localhost:8081/transaction/saveTransactions', inputdata,{ responseType: 'json'});
  }
  updateTransaction(inputdata: any,transId:string) {
    console.log("Input data for saving account:"+JSON.stringify(inputdata));
    return this.http.put('http://localhost:8081/transaction/Transactions/'+transId+'', inputdata,{ responseType: 'json'});
  }
  getTransactionById(transId:string) :Observable<TransactionModel>{
    console.log("Input data for strans id:"+JSON.stringify(transId));
    return this.http.get<TransactionModel>('http://localhost:8081/transaction/Transactions/'+transId+'', { responseType: 'json'});
  }
  deleteTransactionById(transId:string) {
    console.log("Input data for trans id:"+JSON.stringify(transId));
    return this.http.delete('http://localhost:8081/transaction/Transactions/'+transId+'', { responseType: 'text'});
  }
  getClients():Observable<ClientModel[]> {
    return this.http.get<ClientModel[]>('http://localhost:8081/accounts/'+this.getFMId()+'/AllClients',{ responseType: 'json'});
  }
  getAccounts():Observable<AccountModel[]> {
    return this.http.get<AccountModel[]>('http://localhost:8081/accounts/Accounts/AllUnderFM/'+this.getFMId(),{ responseType: 'json'});
  }

  GenerateNewCode(username: any) {
    const params = new HttpParams().set('username', username);
    return this.http.post('http://localhost:8081/user-service/generateNewCodeDirect',  params,{ context: new HttpContext().set(BYPASS_LOG, true) ,responseType: 'text'});
  }
  VerifyCode(input: any) {
    return this.http.post('http://localhost:8081/user-service/verifyCode',  input,{ context: new HttpContext().set(BYPASS_LOG, true) ,responseType: 'text'});
  }

  GetRole() {
    var token = localStorage.getItem('token');
    if (token != null && this.IsLoogedIn()) {
      var extractdata = JSON.parse(Buffer.from(token.split('.')[1], 'base64').toString());
      return extractdata.role;
    }else{
      return '';
    }

  }
  GetId() {
    const fm: FundManagerModel={
      fmName: undefined,
      activeStatus: true,
      deleteStatus: undefined,
      userInfo: undefined,
      fmid: null
    }
    var token = localStorage.getItem('token');
    if (token != null) {
      var extractdata = JSON.parse(Buffer.from(token.split('.')[1], 'base64').toString());
      fm.fmid=extractdata.id;
      return fm;
    }else{
      return '';
    }
  }
  getFMId(){
    var token = localStorage.getItem('token');
    if (token != null) {
      var extractdata = JSON.parse(Buffer.from(token.split('.')[1], 'base64').toString());
      return extractdata.id;
    }else{
      return '';
    }
  }
  getUsername(){
    var token = localStorage.getItem('token');
    if (token != null) {
      var extractdata = JSON.parse(Buffer.from(token.split('.')[1], 'base64').toString());
      return extractdata.sub;
    }else{
      return '';
    }
  }
  clearToken() {
    localStorage.removeItem('token');
  }
}
