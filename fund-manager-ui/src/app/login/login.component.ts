import { Component, OnInit } from '@angular/core';
import { UserService } from '../Service/user.service';
import { NgForm } from '@angular/forms';
import { Route,Router } from '@angular/router';
import { AppRoutingModule } from '../app-routing.module';
import * as alertify from 'alertifyjs'
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private userService: UserService,private route:Router) { }
  ngOnInit(): void {
  }
  respdata:any;
  formSubmitted(input:NgForm){
    console.log(input.value);
    if(input.valid){
      
      this.userService.ProceedLogin(input.value).subscribe(item => {
        this.respdata=item;
        if(this.respdata!=null){
          console.log("Resp data:"+JSON.stringify(this.respdata));
          
          localStorage.setItem('token',this.respdata.body);
          //localStorage.setItem('token',"eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlJPTEVfQ0xJRU5UIl0sImlkIjoxMSwic3ViIjoia3Jpc2huYTE0MzAxMCIsImlhdCI6MTcwMDc4MzQ4NiwiZXhwIjoxNzAwNzg1Mjg2fQ.O3tVF3AxSASVQi2Zd9nc3MQpsdYV4cUpidnm3CaIdVM");
          if(this.respdata.status==206){
            this.route.navigate(['changePassword']);
          }else{
            this.route.navigate(['home']);
          }
          

        }else{
          //alert("Login Failed");
          alertify.error("Log In Failed");
        }
      },
      error => {
        if(JSON.parse(error.error).statusCode==401){
          alertify.error("Invalid Credentials");
        }else{
          alertify.error("Your Account is Not yet Active. Please wait for Activation");
        }
        
      }
      );
    }
  }
  redirectToRegister(){
    console.log('redirecting');
    this.route.navigate(['register']);
  }

}
