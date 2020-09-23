import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDailyReservation, DailyReservation } from 'app/shared/model/daily-reservation.model';
import { DailyReservationService } from './daily-reservation.service';
import { IDeskgroup } from 'app/shared/model/deskgroup.model';
import { DeskgroupService } from 'app/entities/deskgroup/deskgroup.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IDeskgroup | IUser;

@Component({
  selector: 'jhi-daily-reservation-update',
  templateUrl: './daily-reservation-update.component.html',
})
export class DailyReservationUpdateComponent implements OnInit {
  isSaving = false;
  deskgroups: IDeskgroup[] = [];
  users: IUser[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [null, [Validators.required]],
    deskgroupId: [],
    userId: [],
  });

  constructor(
    protected dailyReservationService: DailyReservationService,
    protected deskgroupService: DeskgroupService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dailyReservation }) => {
      this.updateForm(dailyReservation);

      this.deskgroupService.query().subscribe((res: HttpResponse<IDeskgroup[]>) => (this.deskgroups = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(dailyReservation: IDailyReservation): void {
    this.editForm.patchValue({
      id: dailyReservation.id,
      date: dailyReservation.date,
      deskgroupId: dailyReservation.deskgroupId,
      userId: dailyReservation.userId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dailyReservation = this.createFromForm();
    if (dailyReservation.id !== undefined) {
      this.subscribeToSaveResponse(this.dailyReservationService.update(dailyReservation));
    } else {
      this.subscribeToSaveResponse(this.dailyReservationService.create(dailyReservation));
    }
  }

  private createFromForm(): IDailyReservation {
    return {
      ...new DailyReservation(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value,
      deskgroupId: this.editForm.get(['deskgroupId'])!.value,
      userId: this.editForm.get(['userId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDailyReservation>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
