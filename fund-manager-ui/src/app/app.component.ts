import { Component, DoCheck } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from './Service/user.service';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements DoCheck {
  constructor(private route: Router, private service: UserService) { }
  title = 'angular-learn';
  currentRole: string='';
  ngDoCheck(): void {
    console.log("Get Role Response:"+this.service.GetRole());
    this.currentRole=this.service.GetRole()?this.service.GetRole():'';
  }
}
