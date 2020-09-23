import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFloor, Floor } from 'app/shared/model/floor.model';
import { FloorService } from './floor.service';
import { IBuilding } from 'app/shared/model/building.model';
import { BuildingService } from 'app/entities/building/building.service';

@Component({
  selector: 'jhi-floor-update',
  templateUrl: './floor-update.component.html',
})
export class FloorUpdateComponent implements OnInit {
  isSaving = false;
  buildings: IBuilding[] = [];

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]],
    buildingId: [],
  });

  constructor(
    protected floorService: FloorService,
    protected buildingService: BuildingService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ floor }) => {
      this.updateForm(floor);

      this.buildingService.query().subscribe((res: HttpResponse<IBuilding[]>) => (this.buildings = res.body || []));
    });
  }

  updateForm(floor: IFloor): void {
    this.editForm.patchValue({
      id: floor.id,
      identifier: floor.identifier,
      buildingId: floor.buildingId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const floor = this.createFromForm();
    if (floor.id !== undefined) {
      this.subscribeToSaveResponse(this.floorService.update(floor));
    } else {
      this.subscribeToSaveResponse(this.floorService.create(floor));
    }
  }

  private createFromForm(): IFloor {
    return {
      ...new Floor(),
      id: this.editForm.get(['id'])!.value,
      identifier: this.editForm.get(['identifier'])!.value,
      buildingId: this.editForm.get(['buildingId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFloor>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IBuilding): any {
    return item.id;
  }
}
