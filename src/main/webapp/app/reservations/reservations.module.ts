import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReservationsRoutingModule } from './reservations-routing.module';
import { NewReservationComponent } from './new-reservation/new-reservation.component';
import { AllReservationsComponent } from './all-reservations/all-reservations.component';
import { ReservationsAdminComponent } from './admin/admin.component';

@NgModule({
  declarations: [NewReservationComponent, AllReservationsComponent, ReservationsAdminComponent],
  imports: [CommonModule, ReservationsRoutingModule],
})
export class ReservationsModule {}
