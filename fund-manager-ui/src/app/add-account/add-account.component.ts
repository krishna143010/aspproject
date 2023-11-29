import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/Service/user.service';
import * as alertify from 'alertifyjs';
import { BehaviorSubject } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { ClientModel } from '../Model/ClinetModel';


@Component({
  selector: 'app-add-account',
  templateUrl: './add-account.component.html',
  styleUrls: ['./add-account.component.scss']
})
export class AddAccountComponent implements OnInit {

  constructor(private router: Router, private service: UserService) { }
  public isLoading: BehaviorSubject<boolean> = new BehaviorSubject(false);
  public avlClients: ClientModel[] = [];
  ngOnInit(): void {
    this.service.getClients().subscribe(
      item => {
        this.avlClients=item;
      },
      error => {
        this.isLoading = new BehaviorSubject(false);
        console.log(JSON.stringify(error));
        alertify.error('Failed: ' + JSON.stringify(JSON.parse(error.error).message));
      }
    );
  }

  addAccountForm = new FormGroup({
    clients: new FormControl('', Validators.compose([Validators.required])),
    accountName: new FormControl('', Validators.compose([Validators.required,Validators.pattern('^[a-zA-Z ]{3,16}$')])),
    accountNumber: new FormControl('', Validators.compose([Validators.required,Validators.pattern('^[0-9]{3,16}$')])),
    upiId: new FormControl('', Validators.compose([Validators.required,Validators.pattern('^[0-9a-zA-Z @]{3,16}$')])),

  });
  SaveUser() {
    if (this.addAccountForm.valid) {
      this.isLoading = new BehaviorSubject(true);
      this.service.saveAccount(this.addAccountForm.value).subscribe(
        item => {
          this.isLoading = new BehaviorSubject(false);
          alertify.success("Account added");
        },
        error => {
          this.isLoading = new BehaviorSubject(false);
          console.log(JSON.stringify(error));
          alertify.error('Failed: ' + JSON.stringify(error.error.message));
        }
      );
    }

  }

}
