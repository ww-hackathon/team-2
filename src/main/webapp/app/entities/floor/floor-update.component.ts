import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFloor, Floor } from 'app/shared/model/floor.model';
import { FloorService } from './floor.service';

@Component({
  selector: 'jhi-floor-update',
  templateUrl: './floor-update.component.html',
})
export class FloorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]],
  });

  constructor(protected floorService: FloorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ floor }) => {
      this.updateForm(floor);
    });
  }

  updateForm(floor: IFloor): void {
    this.editForm.patchValue({
      id: floor.id,
      identifier: floor.identifier,
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
}
