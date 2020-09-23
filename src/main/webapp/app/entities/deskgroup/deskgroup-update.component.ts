import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDeskgroup, Deskgroup } from 'app/shared/model/deskgroup.model';
import { DeskgroupService } from './deskgroup.service';

@Component({
  selector: 'jhi-deskgroup-update',
  templateUrl: './deskgroup-update.component.html',
})
export class DeskgroupUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    seats: [null, [Validators.required, Validators.min(1)]],
    identifier: [null, [Validators.required]],
    availableSeats: [null, [Validators.required]],
  });

  constructor(protected deskgroupService: DeskgroupService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deskgroup }) => {
      this.updateForm(deskgroup);
    });
  }

  updateForm(deskgroup: IDeskgroup): void {
    this.editForm.patchValue({
      id: deskgroup.id,
      seats: deskgroup.seats,
      identifier: deskgroup.identifier,
      availableSeats: deskgroup.availableSeats,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deskgroup = this.createFromForm();
    if (deskgroup.id !== undefined) {
      this.subscribeToSaveResponse(this.deskgroupService.update(deskgroup));
    } else {
      this.subscribeToSaveResponse(this.deskgroupService.create(deskgroup));
    }
  }

  private createFromForm(): IDeskgroup {
    return {
      ...new Deskgroup(),
      id: this.editForm.get(['id'])!.value,
      seats: this.editForm.get(['seats'])!.value,
      identifier: this.editForm.get(['identifier'])!.value,
      availableSeats: this.editForm.get(['availableSeats'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeskgroup>>): void {
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
