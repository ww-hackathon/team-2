import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeskgroup } from 'app/shared/model/deskgroup.model';
import { DeskgroupService } from './deskgroup.service';
import { DeskgroupDeleteDialogComponent } from './deskgroup-delete-dialog.component';

@Component({
  selector: 'jhi-deskgroup',
  templateUrl: './deskgroup.component.html',
})
export class DeskgroupComponent implements OnInit, OnDestroy {
  deskgroups?: IDeskgroup[];
  eventSubscriber?: Subscription;

  constructor(protected deskgroupService: DeskgroupService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.deskgroupService.query().subscribe((res: HttpResponse<IDeskgroup[]>) => (this.deskgroups = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDeskgroups();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDeskgroup): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDeskgroups(): void {
    this.eventSubscriber = this.eventManager.subscribe('deskgroupListModification', () => this.loadAll());
  }

  delete(deskgroup: IDeskgroup): void {
    const modalRef = this.modalService.open(DeskgroupDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.deskgroup = deskgroup;
  }
}
