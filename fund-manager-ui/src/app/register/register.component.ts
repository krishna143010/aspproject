import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/Service/user.service';
import * as alertify from 'alertifyjs';
import { BehaviorSubject } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { VerifyUserComponent } from '../verify-user/verify-user.component';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  constructor(private router: Router, private service: UserService, private dialog: MatDialog) { }

  ngOnInit(): void {
  }
  respdata: any;
  public isLoading: BehaviorSubject<boolean> = new BehaviorSubject(false);

  RedirectLogin() {
    this.router.navigate(['login']);
  }
  reactiveform = new FormGroup({
    username: new FormControl('', Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_-]{3,16}$')])),
    password: new FormControl('', Validators.compose([Validators.required, Validators.pattern('^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@])[A-Za-z\\d@]{8,}$')])),
    email: new FormControl('', Validators.compose([Validators.required, Validators.email]))
  });
  SaveUser() {
    if (this.reactiveform.valid) {
      this.isLoading = new BehaviorSubject(true);
      this.service.Registeration(this.reactiveform.value).subscribe(
        item => {
          this.isLoading = new BehaviorSubject(false);
          alertify.success("Registerted successfully please verify your email");
          //this.RedirectLogin();
          console.log("Sending Username to Dialogue" + this.reactiveform.get('username')?.value);

          let popup = this.dialog.open(VerifyUserComponent, {
            width: '400px',
            data: {
              username: this.reactiveform.get('username')?.value
            }
          })
          popup.afterClosed().subscribe(item => {
            this.RedirectLogin();
          });
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


