import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { UserModel } from '../Model/UserModel';
import { UserMasterService } from '../Service/user-master.service';
import * as alertify from 'alertifyjs'
import { MatDialog } from '@angular/material/dialog';
import { ModalpopupComponent } from '../modalpopup/modalpopup.component';
import { UserService } from '../Service/user.service';
import { TransactionModel } from '../Model/TransactionModel';
import { MatSort } from '@angular/material/sort';
import { EditTransactionComponent } from '../edit-transaction/edit-transaction.component';
import { Router } from '@angular/router';
// import {LiveAnnouncer} from '@angular/cdk/a11y';


@Component({
  selector: 'app-transaction-statement',
  templateUrl: './transaction-statement.component.html',
  styleUrls: ['./transaction-statement.component.scss']
})
export class TransactionStatementComponent implements OnInit {

  displayedColumns: string[] = ['type', 'fromClientId', 'toClientId', 'fromAccountId', 'toAccountId', 'date', 'amount', 'remarks', 'Action'];
  dataSource = new MatTableDataSource<TransactionModel>;
  posts: any;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  constructor(private service: UserService, private dialog: MatDialog, private router: Router) {
    //this.sortedData = this.Transactions.slice();
    this.refreshData();
  }
  refreshData() {
    this.service.GetAllTransactions().subscribe(item => {
      // this.Transactions = item;
      // this.dataSource = new MatTableDataSource<TransactionModel>(this.Transactions);
      // this.dataSource.paginator = this.paginator;
      this.posts = item;
      this.dataSource = new MatTableDataSource(this.posts);
      this.dataSource.paginator = this.paginator
      this.dataSource.sort = this.sort
    });
  }
  //@ViewChild(MatSort) sort!: MatSort;

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  ngOnInit(): void {
  }

  transactionUpdate(transIDForEdit: any) {
    console.log("Sending UID to Dialogue" + transIDForEdit);

    let popup = this.dialog.open(EditTransactionComponent, {
      width: '800px',
      data: {
        transID: transIDForEdit
      }
    })
    popup.afterClosed().subscribe(item => {
      console.log("Dialogue closed");
      this.refreshData();
    });

  }
  transactionDelete(transID: any) {
    alertify.confirm("Delete Transaction", "do you want remove this transaction:"+transID, () => {
      this.service.deleteTransactionById(transID).subscribe(item => {
        alertify.success('Record Deleted');
        this.refreshData();
      },
        error => {
          alertify.error('Record Delete failed' + JSON.stringify(error));
        }
      );
    }, function () { })

  }

}

