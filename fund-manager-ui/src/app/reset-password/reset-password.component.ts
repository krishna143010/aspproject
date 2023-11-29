import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/Service/user.service';
import * as alertify from 'alertifyjs';
import { BehaviorSubject } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPassword implements OnInit {

  constructor(private router: Router, private service: UserService) { }
  public isLoading: BehaviorSubject<boolean> = new BehaviorSubject(false);

  ngOnInit(): void {
  }

  resetPsw = new FormGroup({
    email: new FormControl('', Validators.compose([Validators.required, Validators.email]))
  });
  ResetPassword() {
    if (this.resetPsw.valid) {
      this.isLoading = new BehaviorSubject(true);
      this.service.resetPassword(this.resetPsw.value).subscribe(
        item => {
          this.isLoading = new BehaviorSubject(false);
          this.router.navigate(['login']);
          alertify.success("Password reset donw! Check your email.");
        },
        error => {
          this.isLoading = new BehaviorSubject(false);
          console.log(JSON.stringify(error));
          alertify.error('Failed: ' + JSON.stringify(JSON.parse(error.error).message));
        }
      );
    }

  }

}
