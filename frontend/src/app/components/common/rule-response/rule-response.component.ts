import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-rule-response',
  templateUrl: './rule-response.component.html',
  styleUrls: ['./rule-response.component.scss']
})
export class RuleResponseComponent implements OnInit {

  constructor() { }

  @Input() small: boolean;

  ngOnInit(): void {
  }

}
