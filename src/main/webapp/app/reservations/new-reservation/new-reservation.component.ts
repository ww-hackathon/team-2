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
import { dateNotBefore } from './new-reservation.validators';
import { Reservation } from './reservation.interface';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { forkJoin } from 'rxjs';
import { MatPaginator } from '@angular/material/paginator';
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

  @ViewChild(MatPaginator) paginator: MatPaginator;

  private _sort: MatSort;

  today = new Date();

  form = this.fb.group({
    startDate: this.fb.control({ value: this.today, disabled: true }, [dateNotBefore(this.today)]),
    endDate: this.fb.control({ value: '', disabled: false }),
    building: this.fb.control(''),
  });

  buildings$ = this.buildingService.queryAndSort();

  dataSource: MatTableDataSource<Reservation> = new MatTableDataSource();

  loaded = false;

  bookedSeat: string = '';

  displayedColumns = ['building', 'floor', 'wing', 'deskgroup', 'action-buttons'];

  account: Account;

  dateFilter = (date: Date) => date.getUTCDay() != 5 && date.getUTCDay() != 6;

  constructor(
    private fb: FormBuilder,
    private buildingService: BuildingService,
    private reservationService: DailyReservationService,
    private accountService: AccountService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
  }

  ngAfterViewInit() {}

  onSubmit() {
    this.bookedSeat = '';
    console.log(this.account);
    if (!this.form.valid) {
      return;
    }

    this.loaded = false;
    const startDate = this.form.get('startDate').value as Date;
    const endDate = this.form.get('endDate').value as Date;

    this.reservationService
      .getOpenReservations(startDate, endDate)
      .pipe(
        map((res: Reservation[]) => {
          // const onlyTen = res.slice(0, 100);
          this.loaded = true;
          this.dataSource = new MatTableDataSource<Reservation>(res);
          this.dataSource.sort = this._sort;
          this.dataSource.paginator = this.paginator;
        })
      )
      .subscribe();
  }

  filterFloor(floor: string) {
    const filterValue = floor;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  filterWing(wing: string) {}

  onShowSeatPlan(reservation: IDailyReservation) {
    console.log(reservation);
    this.dialog.open(SeatPlanComponent, {
      data: {
        reservation,
      },
    });
  }

  onMakeReservationClick(element: Reservation) {
    const startDate = this.form.get('startDate').value as Date;
    const endDate = this.form.get('endDate').value as Date;

    const startDateNoTime = new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate());
    const endDateNoTime = new Date(endDate.getFullYear(), endDate.getMonth(), endDate.getDate());

    let requests = [];

    while (startDateNoTime <= endDateNoTime) {
      requests.push(
        this.reservationService.createNewReservation({
          date: new Date(startDateNoTime.getTime() - startDateNoTime.getTimezoneOffset() * 60 * 1000).toISOString().split('T')[0],
          deskgroupId: element.deskgroupDTO.id,
          userId: this.account['id'],
        })
      );
      startDateNoTime.setDate(startDateNoTime.getDate() + 1);
    }

    forkJoin(requests).subscribe(() => {
      this.bookedSeat = `Sitzplatz C${element.buildingDTO.identifier}.${element.floorDTO.identifier}.${element.wingDTO.identifier}.${element.deskgroupDTO.identifier} erfolgreich gebucht.`;
    });
  }
}
