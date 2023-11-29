import { Component, OnInit } from '@angular/core';
import { UserService } from '../Service/user.service';
import { Router } from '@angular/router';
import * as alertify from 'alertifyjs'
import { NgForm } from '@angular/forms';
@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {

  constructor(private userService: UserService,private route:Router) { 
    this.username = this.userService.getUsername();
  }
  username!: string;
  password!: string;
  passwordRepeat!: string;
  passwordRegex = new RegExp('^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@])[A-Za-z\\d@]{8,}$');

  ngOnInit(): void {
  }
  respdata:any;
  formSubmitted(input:NgForm){
    console.log(input.value);
    if(input.valid){
      if(this.password===this.passwordRepeat){
        if(this.passwordRegex.test(this.password)){
          this.userService.changePassword(this.password).subscribe(item => {
            alertify.success("Password Changed Successfully!");
            this.userService.clearToken();
            this.route.navigate(['login']);
          },
          error => {
            alertify.error("Password Change Failed");
            
          }
          );
        }else{
          alertify.error("Password should contain min 8 letters, @, OneCaps, OneSmall, One Number");
        }
        
      }else{
        alertify.error("Passwords are not matching");
      }
    }
  }
}
