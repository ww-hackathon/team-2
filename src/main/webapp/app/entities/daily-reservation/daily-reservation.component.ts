import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDailyReservation } from 'app/shared/model/daily-reservation.model';
import { DailyReservationService } from './daily-reservation.service';
import { DailyReservationDeleteDialogComponent } from './daily-reservation-delete-dialog.component';

@Component({
  selector: 'jhi-daily-reservation',
  templateUrl: './daily-reservation.component.html',
})
export class DailyReservationComponent implements OnInit, OnDestroy {
  dailyReservations?: IDailyReservation[];
  eventSubscriber?: Subscription;

  constructor(
    protected dailyReservationService: DailyReservationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.dailyReservationService.query().subscribe((res: HttpResponse<IDailyReservation[]>) => (this.dailyReservations = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDailyReservations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDailyReservation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDailyReservations(): void {
    this.eventSubscriber = this.eventManager.subscribe('dailyReservationListModification', () => this.loadAll());
  }

  delete(dailyReservation: IDailyReservation): void {
    const modalRef = this.modalService.open(DailyReservationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dailyReservation = dailyReservation;
  }
}
