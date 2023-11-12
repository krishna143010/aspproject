import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-status',
  template: `
    <h4>
      404 Error. Requested page not found
    </h4>
  `,
  styles: [
    "h4{color:red;font-size:50px;padding-top:30%;}"
  ]
})
export class StatusComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
