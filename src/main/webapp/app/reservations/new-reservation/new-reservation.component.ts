import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { BuildingService } from 'app/entities/building/building.service';
import { DailyReservationService } from 'app/entities/daily-reservation/daily-reservation.service';
import { IDailyReservation } from 'app/shared/model/daily-reservation.model';
import { map } from 'rxjs/operators';
import { SeatPlanComponent } from './seat-plan/seat-plan.component';
/* eslint-disable */

@Component({
  selector: 'jhi-new-reservation',
  templateUrl: './new-reservation.component.html',
  styleUrls: ['./new-reservation.component.scss'],
})
export class NewReservationComponent implements OnInit {
  @ViewChild(MatSort, { static: false }) set content(sort: MatSort) {
    this._sort = sort;
  }

  private _sort: MatSort;

  today = new Date();
  maxDate = new Date(new Date().setDate(this.today.getDate() + 7));

  form = this.fb.group({
    startDate: this.fb.control({ value: this.today, disabled: true }),
    endDate: this.fb.control({ value: '', disabled: false }),
    building: this.fb.control(''),
  });

  buildings$ = this.buildingService.queryAndSort();

  dataSource: MatTableDataSource<IDailyReservation> = new MatTableDataSource();

  loaded = false;

  displayedColumns = ['building', 'floor', 'wing', 'deskgroup', 'action-buttons'];

  dateFilter = (date: Date) => date.getUTCDay() != 5 && date.getUTCDay() != 6;

  constructor(
    private fb: FormBuilder,
    private buildingService: BuildingService,
    private reservationService: DailyReservationService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {}

  ngAfterViewInit() {}

  onSubmit() {
    this.loaded = false;

    this.reservationService
      .getDummyAvailableReservations()
      .pipe(
        map(res => {
          this.loaded = true;
          this.dataSource = new MatTableDataSource(res);
          this.dataSource.sort = this._sort;
        })
      )
      .subscribe();
  }

  onShowSeatPlan(deskgroup: number) {
    this.dialog.open(SeatPlanComponent, {
      data: {
        highlighted: deskgroup,
      },
    });
  }

  onMakeReservationClick(element: IDailyReservation) {
    console.log(element);
  }
}
