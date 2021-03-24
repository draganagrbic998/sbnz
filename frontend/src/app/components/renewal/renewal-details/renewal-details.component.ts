import { Component, Input, OnInit } from '@angular/core';
import { Renewal } from 'src/app/models/renewal';

@Component({
  selector: 'app-renewal-details',
  templateUrl: './renewal-details.component.html',
  styleUrls: ['./renewal-details.component.scss']
})
export class RenewalDetailsComponent implements OnInit {

  constructor() { }

  @Input() renewal: Renewal = {} as Renewal;
  @Input() index = 0;

  ngOnInit(): void {
  }

}
