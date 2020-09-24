import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IDailyReservation } from 'app/shared/model/daily-reservation.model';
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

  constructor(protected modalService: NgbModal, public allReservationService: AllReservationService, private router: Router) {}

  ngOnInit(): void {
    this.allReservationService.getReserVations();
  }

  redirectToCreateReservation(): void {
    this.router.navigate(['/reservations/new']);
  }
}
