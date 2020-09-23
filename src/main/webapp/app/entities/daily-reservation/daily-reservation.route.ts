import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDailyReservation, DailyReservation } from 'app/shared/model/daily-reservation.model';
import { DailyReservationService } from './daily-reservation.service';
import { DailyReservationComponent } from './daily-reservation.component';
import { DailyReservationDetailComponent } from './daily-reservation-detail.component';
import { DailyReservationUpdateComponent } from './daily-reservation-update.component';

@Injectable({ providedIn: 'root' })
export class DailyReservationResolve implements Resolve<IDailyReservation> {
  constructor(private service: DailyReservationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDailyReservation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dailyReservation: HttpResponse<DailyReservation>) => {
          if (dailyReservation.body) {
            return of(dailyReservation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DailyReservation());
  }
}

export const dailyReservationRoute: Routes = [
  {
    path: '',
    component: DailyReservationComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wwHackathonTeam2App.dailyReservation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DailyReservationDetailComponent,
    resolve: {
      dailyReservation: DailyReservationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wwHackathonTeam2App.dailyReservation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DailyReservationUpdateComponent,
    resolve: {
      dailyReservation: DailyReservationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wwHackathonTeam2App.dailyReservation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DailyReservationUpdateComponent,
    resolve: {
      dailyReservation: DailyReservationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wwHackathonTeam2App.dailyReservation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
