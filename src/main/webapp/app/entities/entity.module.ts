import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'deskgroup',
        loadChildren: () => import('./deskgroup/deskgroup.module').then(m => m.WwHackathonTeam2DeskgroupModule),
      },
      {
        path: 'wing',
        loadChildren: () => import('./wing/wing.module').then(m => m.WwHackathonTeam2WingModule),
      },
      {
        path: 'floor',
        loadChildren: () => import('./floor/floor.module').then(m => m.WwHackathonTeam2FloorModule),
      },
      {
        path: 'building',
        loadChildren: () => import('./building/building.module').then(m => m.WwHackathonTeam2BuildingModule),
      },
      {
        path: 'daily-reservation',
        loadChildren: () => import('./daily-reservation/daily-reservation.module').then(m => m.WwHackathonTeam2DailyReservationModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class WwHackathonTeam2EntityModule {}
