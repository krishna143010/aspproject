import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/Service/user.service';
import * as alertify from 'alertifyjs';
import { BehaviorSubject } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { ClientModel } from '../Model/ClinetModel';
import { AccountModel } from '../Model/AccountModel';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.scss']
})
export class TransactionComponent implements OnInit {

  constructor(private router: Router, private service: UserService) { }
  public isLoading: BehaviorSubject<boolean> = new BehaviorSubject(false);
  public avlClients: ClientModel[] = [];
  public avlAccounts: AccountModel[] = [];
  ngOnInit(): void {
    this.isLoading = new BehaviorSubject(true);
    this.service.getClients().subscribe(
      item => {
        this.avlClients=item;
        this.isLoading = new BehaviorSubject(false);
      },
      error => {
        this.isLoading = new BehaviorSubject(false);
        console.log(JSON.stringify(error));
        alertify.error('Failed: ' + JSON.stringify(JSON.parse(error.error).message));
      }
    );
    this.service.getAccounts().subscribe(
      item => {
        this.avlAccounts=item;
        this.isLoading = new BehaviorSubject(false);
      },
      error => {
        this.isLoading = new BehaviorSubject(false);
        console.log(JSON.stringify(error));
        alertify.error('Failed: ' + JSON.stringify(JSON.parse(error.error).message));
      }
    );
  }

  transactionAddForm = new FormGroup({
    fromClientId: new FormControl('', Validators.compose([Validators.required])),
    toClientId: new FormControl('', Validators.compose([Validators.required])),
    fromAccountId: new FormControl('', Validators.compose([Validators.required])),
    toAccountId: new FormControl('', Validators.compose([Validators.required])),
    remarks: new FormControl('', Validators.compose([Validators.pattern('^[a-zA-Z 0-9@/,.]{0,35}$')])),
    amount: new FormControl('', Validators.compose([Validators.required,Validators.pattern('^[0-9]{1,7}$')])),
    date: new FormControl({ value: '', disabled: false },Validators.compose([Validators.required])),

  });
  SaveUser() {
    if (this.transactionAddForm.valid) {
      this.isLoading = new BehaviorSubject(true);
      this.service.saveTransaction(this.transactionAddForm.value).subscribe(
        item => {
          this.isLoading = new BehaviorSubject(false);
          alertify.success("Transaction added");
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
