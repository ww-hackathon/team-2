import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWing } from 'app/shared/model/wing.model';
import { WingService } from './wing.service';
import { WingDeleteDialogComponent } from './wing-delete-dialog.component';

@Component({
  selector: 'jhi-wing',
  templateUrl: './wing.component.html',
})
export class WingComponent implements OnInit, OnDestroy {
  wings?: IWing[];
  eventSubscriber?: Subscription;

  constructor(protected wingService: WingService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.wingService.query().subscribe((res: HttpResponse<IWing[]>) => (this.wings = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWing): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWings(): void {
    this.eventSubscriber = this.eventManager.subscribe('wingListModification', () => this.loadAll());
  }

  delete(wing: IWing): void {
    const modalRef = this.modalService.open(WingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.wing = wing;
  }
}
