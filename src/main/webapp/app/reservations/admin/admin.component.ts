import { Component, OnInit } from '@angular/core';

import { ThresholdService } from './threshold.service';
/* eslint-disable */

@Component({
  selector: 'jhi-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss'],
})
export class ReservationsAdminComponent implements OnInit {
  thresholdValue: any = '';

  constructor(protected thresholdService: ThresholdService) {}

  ngOnInit(): void {
    this.thresholdService.query().subscribe((res: any) => (this.thresholdValue = res.body || ''));
  }

  onThresholdInput(event: any): void {
    this.thresholdValue = event.target.value;
  }

  updateThresholdValue(): void {
    this.thresholdService.update(this.thresholdValue).subscribe((res: any) => {
      //console.log(res);
    });
  }
}
