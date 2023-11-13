import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserMasterService } from '../Service/user-master.service';
import * as alertify from 'alertifyjs'

@Component({
  selector: 'app-modalpopup',
  templateUrl: './modalpopup.component.html',
  styleUrls: ['./modalpopup.component.scss']
})
export class ModalpopupComponent implements OnInit {

  constructor(private service: UserMasterService, @Inject(MAT_DIALOG_DATA) public data: any,
  private ref:MatDialogRef<ModalpopupComponent>) { }

  ngOnInit(): void {
    //this.GetAllRole();
    console.log("Input data:"+JSON.stringify(this.data));
    this.GetExistdata(this.data.userid);
  }

  roledata: any;
  editdata: any;
  savedata: any;

  updateform = new FormGroup({
    id: new FormControl({ value: this.data.userid, disabled: true }),
    activeStatus: new FormControl("", Validators.required),
  })

  SaveUser() {
    console.log("Saving");
    if (this.updateform.valid) {
      this.service.UpdateUser(this.editdata.username,this.updateform.get('activeStatus')?.value).subscribe(item => {
        this.savedata = item;
        alertify.success("Updated successfully.")
        this.ref.close();
      },
      error=>{
        console.log("Updated Failed:"+JSON.stringify(error));
        alertify.error("Updated Failed:"+JSON.stringify(error));
      }
      );
    }
  }

  GetAllRole() {
    // this.service.GetAllRoles().subscribe(item => {
    //   this.roledata = item;
    // });
  }

  GetExistdata(userid: any) {
    this.service.GetUserbyId(userid).subscribe(item => {
      this.editdata = item;
      if (this.editdata != null) {
        console.log("Setting data"+JSON.stringify(this.editdata));
        this.updateform.setValue({ id: "2", activeStatus: this.editdata.activeStatus});
      }
    });

  }

}
