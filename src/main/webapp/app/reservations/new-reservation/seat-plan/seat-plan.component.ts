import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { IDailyReservation } from 'app/shared/model/daily-reservation.model';

@Component({
  selector: 'jhi-seat-plan',
  templateUrl: './seat-plan.component.html',
  styleUrls: ['./seat-plan.component.scss'],
})
export class SeatPlanComponent implements OnInit {
  constructor(@Inject(MAT_DIALOG_DATA) public data: { reservation: IDailyReservation }) {}

  ngOnInit(): void {}
}
