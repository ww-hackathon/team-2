import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBuilding } from 'app/shared/model/building.model';
import { BuildingService } from './building.service';
import { BuildingDeleteDialogComponent } from './building-delete-dialog.component';

@Component({
  selector: 'jhi-building',
  templateUrl: './building.component.html',
})
export class BuildingComponent implements OnInit, OnDestroy {
  buildings?: IBuilding[];
  eventSubscriber?: Subscription;

  constructor(protected buildingService: BuildingService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.buildingService.query().subscribe((res: HttpResponse<IBuilding[]>) => (this.buildings = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBuildings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBuilding): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBuildings(): void {
    this.eventSubscriber = this.eventManager.subscribe('buildingListModification', () => this.loadAll());
  }

  delete(building: IBuilding): void {
    const modalRef = this.modalService.open(BuildingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.building = building;
  }
}
