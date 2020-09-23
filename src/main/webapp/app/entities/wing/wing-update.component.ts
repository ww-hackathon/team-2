import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWing, Wing } from 'app/shared/model/wing.model';
import { WingService } from './wing.service';

@Component({
  selector: 'jhi-wing-update',
  templateUrl: './wing-update.component.html',
})
export class WingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]],
  });

  constructor(protected wingService: WingService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wing }) => {
      this.updateForm(wing);
    });
  }

  updateForm(wing: IWing): void {
    this.editForm.patchValue({
      id: wing.id,
      identifier: wing.identifier,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const wing = this.createFromForm();
    if (wing.id !== undefined) {
      this.subscribeToSaveResponse(this.wingService.update(wing));
    } else {
      this.subscribeToSaveResponse(this.wingService.create(wing));
    }
  }

  private createFromForm(): IWing {
    return {
      ...new Wing(),
      id: this.editForm.get(['id'])!.value,
      identifier: this.editForm.get(['identifier'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWing>>): void {
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
