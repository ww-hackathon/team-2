import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DailyReservationService } from 'app/entities/daily-reservation/daily-reservation.service';
import { IDailyReservation } from 'app/shared/model/daily-reservation.model';
import { Observable } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import { AllReservationService } from './all-reservation.service';
import { ReservationDeleteDialogComponent } from './reservation-delete-dialog/reservation-delete-dialog.component';
/* eslint-disable */

@Component({
  selector: 'jhi-all-reservations',
  templateUrl: './all-reservations.component.html',
  styleUrls: ['./all-reservations.component.scss'],
})
export class AllReservationsComponent implements OnInit {
  displayedColumns = ['building', 'floor', 'wing', 'deskgroup', 'date', 'action-buttons'];

  onDeleteReservationClick(element: IDailyReservation) {
    console.log(element);
    const modalRef = this.modalService.open(ReservationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.id = element.id;
  }

  constructor(
    private reservationService: DailyReservationService,
    protected modalService: NgbModal,
    public allReservationService: AllReservationService
  ) {}

  ngOnInit(): void {
    this.allReservationService.getReserVations();
  }
}
