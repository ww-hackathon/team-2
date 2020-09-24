import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { DateAdapter, MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSortModule } from '@angular/material/sort';

import { ReservationsRoutingModule } from './reservations-routing.module';
import { NewReservationComponent } from './new-reservation/new-reservation.component';
import { AllReservationsComponent } from './all-reservations/all-reservations.component';
import { ReservationsAdminComponent } from './admin/admin.component';
import { CustomDatePickerAdapter } from '../shared/util/mat-datepicker-adapter';
import { SeatPlanComponent } from './new-reservation/seat-plan/seat-plan.component';

@NgModule({
  declarations: [NewReservationComponent, AllReservationsComponent, ReservationsAdminComponent, SeatPlanComponent],
  imports: [
    CommonModule,
    ReservationsRoutingModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatNativeDateModule,
    MatSelectModule,
    MatTableModule,
    MatSortModule,
    MatIconModule,
    MatDialogModule,
    ReactiveFormsModule,
  ],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'de-DE' },
    { provide: DateAdapter, useClass: CustomDatePickerAdapter },
  ],
  entryComponents: [SeatPlanComponent],
})
export class ReservationsModule {}
