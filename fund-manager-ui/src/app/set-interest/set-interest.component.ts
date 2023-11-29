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
  selector: 'app-set-interest',
  templateUrl: './set-interest.component.html',
  styleUrls: ['./set-interest.component.scss']
})
export class SetInterestRateComponent implements OnInit {

  constructor(private router: Router, private service: UserService) { }
  public isLoading: BehaviorSubject<boolean> = new BehaviorSubject(false);
  public avlAccounts: AccountModel[] = [];
  ngOnInit(): void {
    this.service.getAccounts().subscribe(
      item => {
        this.avlAccounts=item;
      },
      error => {
        this.isLoading = new BehaviorSubject(false);
        console.log(JSON.stringify(error));
        alertify.error('Failed: ' + JSON.stringify(JSON.parse(error.error).message));
      }
    );
  }

  setInterestForm = new FormGroup({
    accountId: new FormControl('', Validators.compose([Validators.required])),
    startDate: new FormControl({ value: '', disabled: false },Validators.compose([Validators.required])),
    interestRate: new FormControl('', Validators.compose([Validators.required,Validators.min(0.01)]))

  });
  setInterest() {
    if (this.setInterestForm.valid) {
      this.isLoading = new BehaviorSubject(true);
      this.service.settleInterest(this.setInterestForm.value).subscribe(
        item => {
          this.isLoading = new BehaviorSubject(false);
          alertify.success("Interest rate set.");
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
