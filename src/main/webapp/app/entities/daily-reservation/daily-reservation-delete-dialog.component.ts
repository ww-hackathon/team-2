import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDailyReservation } from 'app/shared/model/daily-reservation.model';
import { DailyReservationService } from './daily-reservation.service';

@Component({
  templateUrl: './daily-reservation-delete-dialog.component.html',
})
export class DailyReservationDeleteDialogComponent {
  dailyReservation?: IDailyReservation;

  constructor(
    protected dailyReservationService: DailyReservationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dailyReservationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dailyReservationListModification');
      this.activeModal.close();
    });
  }
}
