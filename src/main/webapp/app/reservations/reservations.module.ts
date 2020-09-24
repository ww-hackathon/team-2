import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatNativeDateModule } from '@angular/material/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';

import { ReservationsRoutingModule } from './reservations-routing.module';
import { NewReservationComponent } from './new-reservation/new-reservation.component';
import { AllReservationsComponent } from './all-reservations/all-reservations.component';
import { ReservationsAdminComponent } from './admin/admin.component';

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
    ReactiveFormsModule,
  ],
})
export class ReservationsModule {}
