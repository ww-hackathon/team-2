import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { DailyReservationService } from 'app/entities/daily-reservation/daily-reservation.service';
import { IDailyReservation } from 'app/shared/model/daily-reservation.model';
import { Observable } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
/* eslint-disable */

@Component({
  selector: 'jhi-all-reservations',
  templateUrl: './all-reservations.component.html',
  styleUrls: ['./all-reservations.component.scss'],
})
export class AllReservationsComponent implements OnInit {
  displayedColumns = ['building', 'floor', 'wing', 'deskgroup', 'date', 'action-buttons'];

  dataSource: MatTableDataSource<IDailyReservation>;

  dataSource$: Observable<void> = this.reservationService.query().pipe(
    map(data => {
       this.dataSource = new MatTableDataSource(data.body as IDailyReservation[]);
    })
  );

  onDeleteReservationClick(element: IDailyReservation) {
    console.log(element);
    this.reservationService
      .delete(element.id!)
      .pipe(
        switchMap(() => {
          return this.dataSource$;
        })
      )
      .subscribe();
  }

  constructor(private reservationService: DailyReservationService) {}

  ngOnInit(): void {
    this.dataSource$.subscribe();
  }
}
