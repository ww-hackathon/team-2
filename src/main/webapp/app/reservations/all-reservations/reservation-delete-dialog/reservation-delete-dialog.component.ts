import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { AllReservationService } from '../all-reservation.service';
/* eslint-disable */
@Component({
  selector: 'jhi-reservation-delete-dialog',
  templateUrl: './reservation-delete-dialog.component.html',
  styleUrls: ['./reservation-delete-dialog.component.scss'],
})
export class ReservationDeleteDialogComponent {
  id?: any;

  constructor(
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
    public allReservationService: AllReservationService
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(): void {
    console.log(this.id);
    this.allReservationService.cancelReservation(this.id);
    this.activeModal.dismiss();
  }
}
