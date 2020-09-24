import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { BuildingService } from 'app/entities/building/building.service';
import { DailyReservationService } from 'app/entities/daily-reservation/daily-reservation.service';
import { IDailyReservation } from 'app/shared/model/daily-reservation.model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
/* eslint-disable */

@Component({
  selector: 'jhi-new-reservation',
  templateUrl: './new-reservation.component.html',
  styleUrls: ['./new-reservation.component.scss'],
})
export class NewReservationComponent implements OnInit {
  @ViewChild(MatSort) sort: MatSort;

  today = new Date();
  maxDate = new Date(new Date().setDate(this.today.getDate() + 7));

  form = this.fb.group({
    startDate: this.fb.control({ value: this.today, disabled: true }),
    endDate: this.fb.control({ value: '', disabled: true }),
    building: this.fb.control(''),
  });

  buildings$ = this.buildingService.queryAndSort();

  dataSource$: Observable<MatTableDataSource<IDailyReservation>> = this.reservationService.getDummyAvailableReservations().pipe(
    map(data => {
      const dataSource = new MatTableDataSource(data);
      dataSource.sort = this.sort;

      return dataSource;
    })
  );

  displayedColumns = ['building', 'floor', 'wing', 'deskgroup'];

  constructor(private fb: FormBuilder, private buildingService: BuildingService, private reservationService: DailyReservationService) {}

  // ngAfterViewInit() {
  //   this.dataSource.sort = this.sort;
  // }

  ngOnInit(): void {}

  onSubmit() {
    console.log(this.form.value);
  }

  dateFilter(date: Date) {
    const notSatSun = date.getUTCDay() != 5 && date.getUTCDay() != 6;
    return notSatSun;
  }
}
