import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WwHackathonTeam2SharedModule } from 'app/shared/shared.module';
import { DailyReservationComponent } from './daily-reservation.component';
import { DailyReservationDetailComponent } from './daily-reservation-detail.component';
import { DailyReservationUpdateComponent } from './daily-reservation-update.component';
import { DailyReservationDeleteDialogComponent } from './daily-reservation-delete-dialog.component';
import { dailyReservationRoute } from './daily-reservation.route';

@NgModule({
  imports: [WwHackathonTeam2SharedModule, RouterModule.forChild(dailyReservationRoute)],
  declarations: [
    DailyReservationComponent,
    DailyReservationDetailComponent,
    DailyReservationUpdateComponent,
    DailyReservationDeleteDialogComponent,
  ],
  entryComponents: [DailyReservationDeleteDialogComponent],
})
export class WwHackathonTeam2DailyReservationModule {}
