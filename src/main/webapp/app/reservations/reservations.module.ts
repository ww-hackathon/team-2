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

import { ReservationsRoutingModule } from './reservations-routing.module';
import { NewReservationComponent } from './new-reservation/new-reservation.component';
import { AllReservationsComponent } from './all-reservations/all-reservations.component';
import { ReservationsAdminComponent } from './admin/admin.component';
import { CustomDatePickerAdapter } from '../shared/util/mat-datepicker-adapter';

@NgModule({
  declarations: [NewReservationComponent, AllReservationsComponent, ReservationsAdminComponent],
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
    ReactiveFormsModule,
  ],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'de-DE' },
    { provide: DateAdapter, useClass: CustomDatePickerAdapter },
  ],
})
export class ReservationsModule {}
