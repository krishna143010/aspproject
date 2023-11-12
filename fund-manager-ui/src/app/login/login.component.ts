import { Component, OnInit } from '@angular/core';
import { UserService } from '../Service/user.service';
import { NgForm } from '@angular/forms';
import { Route, Router } from '@angular/router';
import { AppRoutingModule } from '../app-routing.module';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private userService: UserService,private route:Router) { }
  ngOnInit(): void {
  }
  formSubmitted(input:NgForm){
    console.log(input.value);
    if(input.valid){
      
      console.log(this.userService.LoginValidation());
      localStorage.setItem("userKey",this.userService.LoginValidation());
      this.route.navigate(['testpath']);
    }else{
      console.log("invalid");
      //alert("Invalid form submission");
    }
  }
  redirectToRegister(){
    console.log('redirecting');
    this.route.navigate(['register']);
  }

}
