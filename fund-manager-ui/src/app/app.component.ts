import { Component, DoCheck } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from './Service/user.service';
import { VerifyUserComponent } from './verify-user/verify-user.component';
import { MatDialog } from '@angular/material/dialog';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements DoCheck {

  logOutClicked() {
    this.service.clearToken();
    this.route.navigate(['home']);
  }
  constructor(private route: Router, private service: UserService, private dialog: MatDialog) { }
  title = 'angular-learn';
  currentRole: string = '';
  ngDoCheck(): void {
    console.log("Get Role Response:" + this.service.GetRole());
    this.currentRole = this.service.GetRole() ? this.service.GetRole() : '';
  }
  loadVerifyUser() {
    //this.route.navigate(['verifyUser']);
    let popup = this.dialog.open(VerifyUserComponent, {
      width: '500px',
      data: {
        username: ''
      }
    })
    popup.afterClosed().subscribe(item => {
      this.route.navigate(['login']);
    });
  }
}
