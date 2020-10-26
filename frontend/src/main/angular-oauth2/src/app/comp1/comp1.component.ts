import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-comp1',
  templateUrl: './comp1.component.html',
  styleUrls: ['./comp1.component.css']
})
export class Comp1Component implements OnInit {

  constructor(private appComponent: AppComponent) { }

  ngOnInit(): void {
    console.log(this.appComponent.accessToken);
    
  }

}
