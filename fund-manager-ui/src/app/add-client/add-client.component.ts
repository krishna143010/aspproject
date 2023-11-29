import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/Service/user.service';
import * as alertify from 'alertifyjs';
import { BehaviorSubject } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-add-client',
  templateUrl: './add-client.component.html',
  styleUrls: ['./add-client.component.scss']
})
export class AddClientComponent implements OnInit {

  constructor(private router: Router, private service: UserService) { }
  public isLoading: BehaviorSubject<boolean> = new BehaviorSubject(false);

  ngOnInit(): void {
  }

  addClientForm = new FormGroup({
    clientName: new FormControl('', Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z ]{3,16}$')])),
    email: new FormControl('', Validators.compose([Validators.required, Validators.email]))
  });
  SaveUser() {
    if (this.addClientForm.valid) {
      this.isLoading = new BehaviorSubject(true);
      this.service.saveClient(this.addClientForm.value).subscribe(
        item => {
          this.isLoading = new BehaviorSubject(false);
          alertify.success("Client added");
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
