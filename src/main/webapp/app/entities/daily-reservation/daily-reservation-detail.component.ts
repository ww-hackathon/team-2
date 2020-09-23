import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDailyReservation } from 'app/shared/model/daily-reservation.model';

@Component({
  selector: 'jhi-daily-reservation-detail',
  templateUrl: './daily-reservation-detail.component.html',
})
export class DailyReservationDetailComponent implements OnInit {
  dailyReservation: IDailyReservation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dailyReservation }) => (this.dailyReservation = dailyReservation));
  }

  previousState(): void {
    window.history.back();
  }
}
