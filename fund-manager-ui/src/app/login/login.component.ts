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
          localStorage.setItem('token',this.respdata);
          this.route.navigate(['home']);

        }else{
          //alert("Login Failed");
          alertify.error("Log In Failed");
        }
      },
      error => {
        alertify.error("Invalid Credentials");
      }
      );
    }
  }
  redirectToRegister(){
    console.log('redirecting');
    this.route.navigate(['register']);
  }

}
