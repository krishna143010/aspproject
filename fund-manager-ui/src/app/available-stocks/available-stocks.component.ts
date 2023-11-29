import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { UserModel } from '../Model/UserModel';
import { UserMasterService } from '../Service/user-master.service';
import * as alertify from 'alertifyjs'
import { MatDialog } from '@angular/material/dialog';
import { ModalpopupComponent } from '../modalpopup/modalpopup.component';
import { UserService } from '../Service/user.service';
import { StockInfo } from '../Model/StockData';





@Component({
  selector: 'app-available-stocks',
  templateUrl: './available-stocks.component.html',
  styleUrls: ['./available-stocks.component.scss']
})
export class AvailableStocks implements OnInit {
FunctionUpdate(arg0: any) {
throw new Error('Method not implemented.');
}

  constructor(private service: UserMasterService,private serviceS: UserService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.GetAllStocks();
  }
  @ViewChild(MatPaginator) paginator !: MatPaginator;

  UserDetail: any;
  dataSource: any;

  // GetAllUser() {
  //   this.service.GetAllUser().subscribe(item => {
  //     this.UserDetail = item;
  //     this.dataSource = new MatTableDataSource<UserModel>(this.UserDetail);
  //     this.dataSource.paginator = this.paginator;
  //   });
  // }
  GetAllStocks() {
    this.serviceS.GetAllStocks().subscribe(item => {
      this.UserDetail = item.data;
      this.dataSource = new MatTableDataSource<StockInfo>(this.UserDetail);
      this.dataSource.paginator = this.paginator;
    });
  }

  //displayedColumns: string[] = ['id', 'username', 'email', 'authenticationStatus', 'enabled', 'roles', 'Action'];
  displayedColumns: string[] = ['symbol', 'lastPrice', 'yearHigh', 'yearLow', 'perChange365d', 'perChange30d', 'Action'];
  //dataSource = ELEMENT_DATA;

  // FunctionUpdate(userid: any) {
  //   console.log("Sending UID to Dialogue"+userid);

  //   let popup = this.dialog.open(ModalpopupComponent, {
  //     width: '400px',
  //     data: {
  //       userid: userid
  //     }
  //   })
  //   popup.afterClosed().subscribe(item => {
  //     this.GetAllStocks();
  //   });

  // }

}
