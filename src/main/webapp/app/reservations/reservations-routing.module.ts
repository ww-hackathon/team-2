import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ReservationsAdminComponent } from './admin/admin.component';
import { AllReservationsComponent } from './all-reservations/all-reservations.component';
import { NewReservationComponent } from './new-reservation/new-reservation.component';

const routes: Routes = [
  { path: 'new', component: NewReservationComponent },
  { path: 'all', component: AllReservationsComponent },
  { path: 'admin', component: ReservationsAdminComponent },
  { path: '*', redirectTo: 'new' },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ReservationsRoutingModule {}
