import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserMasterService } from '../Service/user-master.service';
import * as alertify from 'alertifyjs'
import { UserService } from '../Service/user.service';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { Router } from '@angular/router';


@Component({
  selector: 'app-verify-user',
  templateUrl: './verify-user.component.html',
  styleUrls: ['./verify-user.component.scss']
})
export class VerifyUserComponent implements OnInit {

  constructor(private service: UserService,private route:Router, @Inject(MAT_DIALOG_DATA) public data: any,
  private ref:MatDialogRef<VerifyUserComponent>) { }
  public isLoading: BehaviorSubject<boolean> = new BehaviorSubject(false);
  ngOnInit(): void {
    console.log("Input data:"+JSON.stringify(this.data));
  }
  verifyForm = new FormGroup({
    username: new FormControl({ value: this.data.username?this.data.username:'', disabled: this.data.username?true: false },Validators.compose([Validators.required,Validators.pattern('^[a-zA-Z0-9_-]{3,16}$')])),
    codeSupplied: new FormControl("", Validators.compose([Validators.required,Validators.pattern('^[A-Z0-9]{6}$')]))
  })
  verifyCode() {
    if(this.verifyForm.valid){
      //this.reactiveform.value
      console.log("verify intitated");
      this.isLoading = new BehaviorSubject(true);
      this.service.VerifyCode(this.verifyForm.getRawValue()).subscribe(
        item => {
          this.isLoading = new BehaviorSubject(false);
          alertify.success("Thank you! Your Account is verified.");
          this.route.navigate(['login']);
        },
        error => {
          this.isLoading = new BehaviorSubject(false);
          console.log(JSON.stringify(error));
          alertify.error('Failed to Verify: ' + JSON.stringify(JSON.parse(error.error).message));
        });
    }else{
      alertify.error('Username not entered properly [a-zA-Z0-9_-]{3,16}');
    }
  }

  resendCode(){
    if(this.verifyForm.get('username')?.valid){
      console.log("resend intitated");
      this.isLoading = new BehaviorSubject(true);
      this.service.GenerateNewCode(this.verifyForm.get('username')?.value).subscribe(
        item => {
          this.isLoading = new BehaviorSubject(false);
          alertify.success("New code generated successfully. Kindly enter and verify");
          //this.route.navigate(['login']);
        },
        error => {
          this.isLoading = new BehaviorSubject(false);
          console.log(JSON.stringify(error));
          alertify.error('Failed to Generate code: ' + JSON.stringify(JSON.parse(error.error).message));
        });
    }else{
      alertify.error('Username not entered properly [a-zA-Z0-9_-]{3,16}');
    }
    
  }

}
