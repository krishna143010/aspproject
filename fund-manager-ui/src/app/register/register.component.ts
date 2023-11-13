import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/Service/user.service';
import * as alertify from 'alertifyjs'


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  constructor(private router: Router, private service: UserService) { }

  ngOnInit(): void {
  }
  respdata: any;

  RedirectLogin() {
    this.router.navigate(['login']);
  }
  reactiveform = new FormGroup({
    username: new FormControl('', Validators.compose([Validators.required,Validators.pattern('^[a-zA-Z0-9_-]{3,16}$')])),
    password: new FormControl('', Validators.compose([Validators.required,Validators.pattern('^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@])[A-Za-z\\d@]{8,}$')])),
    email: new FormControl('', Validators.compose([Validators.required, Validators.email]))
  });
  SaveUser() {
    if (this.reactiveform.valid) {
      this.service.Registeration(this.reactiveform.value).subscribe(
        item => {
            alertify.success("Registerted successfully please verify your email");
           this.RedirectLogin();
        },
        error=>{
          console.log(JSON.stringify(error));
          alertify.error('Failed: '+JSON.stringify(JSON.parse(error.error).message));
        }
      );
    }

  }

}


