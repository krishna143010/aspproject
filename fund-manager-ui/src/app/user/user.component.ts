import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { UserModel } from '../Model/UserModel';
import { UserMasterService } from '../Service/user-master.service';
import * as alertify from 'alertifyjs'
import { MatDialog } from '@angular/material/dialog';
import { ModalpopupComponent } from '../modalpopup/modalpopup.component';





@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  constructor(private service: UserMasterService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.GetAllUser();
  }
  @ViewChild(MatPaginator) paginator !: MatPaginator;

  UserDetail: any;
  dataSource: any;

  GetAllUser() {
    this.service.GetAllUser().subscribe(item => {
      this.UserDetail = item;
      this.dataSource = new MatTableDataSource<UserModel>(this.UserDetail);
      this.dataSource.paginator = this.paginator;
    });
  }

  displayedColumns: string[] = ['id', 'username', 'email', 'authenticationStatus', 'enabled', 'roles', 'Action'];
  //dataSource = ELEMENT_DATA;

  FunctionUpdate(userid: any) {
    console.log("Sending UID to Dialogue"+userid);

    let popup = this.dialog.open(ModalpopupComponent, {
      width: '400px',
      data: {
        userid: userid
      }
    })
    popup.afterClosed().subscribe(item => {
      this.GetAllUser();
    });

  }
  FunctionDelete(userid: any) {
    // alertify.confirm("Remove User","do you want remove this user?",()=>{
    //   this.service.RemoveUser(userid).subscribe(item => {
    //     this.GetAllUser();
    //     alertify.success("Removed Successfully");
    //   });

    // },function(){})

  }

}
