import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';
import { DailyReservationService } from 'app/entities/daily-reservation/daily-reservation.service';
import { IDailyReservation } from 'app/shared/model/daily-reservation.model';
import { map, switchMap } from 'rxjs/operators';
/* eslint-disable */

@Injectable({ providedIn: 'root' })
export class AllReservationService {
  public hasData: boolean = true;

  public dataSource: MatTableDataSource<IDailyReservation>;

  public dataSource$: Observable<void> = this.reservationService.query().pipe(
    map((data: any) => {
      console.log(data.body);
      if (data.body.length > 0) {
        this.hasData = true;
      } else {
        this.hasData = false;
      }
      this.dataSource = new MatTableDataSource(data.body as IDailyReservation[]);
    })
  );
  constructor(private reservationService: DailyReservationService) {
    this.getReserVations();
  }

  getReserVations(): void {
    this.dataSource$.subscribe();
  }

  cancelReservation(id: any): void {
    this.reservationService
      .delete(id!)
      .pipe(
        switchMap(() => {
          return this.dataSource$;
        })
      )
      .subscribe();
  }
}
