import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFloor } from 'app/shared/model/floor.model';
import { FloorService } from './floor.service';
import { FloorDeleteDialogComponent } from './floor-delete-dialog.component';

@Component({
  selector: 'jhi-floor',
  templateUrl: './floor.component.html',
})
export class FloorComponent implements OnInit, OnDestroy {
  floors?: IFloor[];
  eventSubscriber?: Subscription;

  constructor(protected floorService: FloorService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.floorService.query().subscribe((res: HttpResponse<IFloor[]>) => (this.floors = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFloors();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFloor): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFloors(): void {
    this.eventSubscriber = this.eventManager.subscribe('floorListModification', () => this.loadAll());
  }

  delete(floor: IFloor): void {
    const modalRef = this.modalService.open(FloorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.floor = floor;
  }
}
