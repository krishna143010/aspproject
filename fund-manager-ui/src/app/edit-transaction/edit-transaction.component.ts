import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserMasterService } from '../Service/user-master.service';
import * as alertify from 'alertifyjs';
import { UserService } from '../Service/user.service';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { Router } from '@angular/router';
import { ClientModel } from '../Model/ClinetModel';
import { AccountModel } from '../Model/AccountModel';
import { TransactionModel } from '../Model/TransactionModel';

@Component({
  selector: 'app-edit-transaction',
  templateUrl: './edit-transaction.component.html',
  styleUrls: ['./edit-transaction.component.scss']
})
export class EditTransactionComponent implements OnInit {


  constructor(private service: UserService, private route: Router, @Inject(MAT_DIALOG_DATA) public data: any,
    private ref: MatDialogRef<EditTransactionComponent>) { 
      
    }
  public isLoading: BehaviorSubject<boolean> = new BehaviorSubject(false);
  public avlClients: ClientModel[] = [];
  public avlAccounts: AccountModel[] = [];
  public transDataLatest!: TransactionModel;
  //transactionAddForm!: FormGroup;
  ngOnInit(): void {
    console.log("Input data:" + JSON.stringify(this.data));

    this.isLoading = new BehaviorSubject(true);
    this.service.getClients().subscribe(
      item => {
        this.avlClients = item;
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
        this.avlAccounts = item;
        this.isLoading = new BehaviorSubject(false);
      },
      error => {
        this.isLoading = new BehaviorSubject(false);
        console.log(JSON.stringify(error));
        alertify.error('Failed: ' + JSON.stringify(JSON.parse(error.error).message));
      }
    );
    this.service.getTransactionById(this.data.transID).subscribe(
      item => {
        this.transDataLatest=item;
        const nameData = { 
          transId: item.transId,
          fromClientId: item.fromClientId,
          toClientId: item.toClientId,
          fromAccountId: item.fromAccountId,
          toAccountId: item.toAccountId,
          remarks: item.remarks,
          amount: item.amount,
          date: item.date

         };
         
        const dataPartial: Partial<TransactionModel> = nameData;
        let toAccount:AccountModel=item.toAccountId;
        this.transactionAddForm.patchValue({
          amount: item.amount.toString(),
          date: item.date.toString(),
          remarks: item.remarks.toString()
        });
        //this.transactionAddForm.controls.fromClientId=item.fromAccountId;
        //this.transactionAddForm.get('fromClientId').setValue(item.fromClientId);
        
        //this.transactionAddForm.patchValue(nameData);
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

    transId: new FormControl({ value: this.data.transID, disabled: true }),
    fromClientId: new FormControl('',Validators.compose([Validators.required])),
    toClientId: new FormControl( '',Validators.compose([Validators.required])),
    fromAccountId: new FormControl('', Validators.compose([Validators.required])),
    toAccountId: new FormControl(Validators.compose([Validators.required])),
    remarks: new FormControl('', Validators.compose([Validators.pattern('^[a-zA-Z 0-9@/,.]{0,35}$')])),
    amount: new FormControl('', Validators.compose([Validators.required, Validators.pattern('^[0-9]{1,7}$')])),
    date: new FormControl({ value: '', disabled: false }, Validators.compose([Validators.required])),

  });
  
  SaveUser() {
    if (this.transactionAddForm.valid) {
      this.isLoading = new BehaviorSubject(true);
      this.service.updateTransaction(this.transactionAddForm.value, this.data.transID).subscribe(
        item => {
          this.isLoading = new BehaviorSubject(false);
          alertify.success("Transaction updated");
          this.ref.close();
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
