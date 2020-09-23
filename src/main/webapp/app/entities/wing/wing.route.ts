import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWing, Wing } from 'app/shared/model/wing.model';
import { WingService } from './wing.service';
import { WingComponent } from './wing.component';
import { WingDetailComponent } from './wing-detail.component';
import { WingUpdateComponent } from './wing-update.component';

@Injectable({ providedIn: 'root' })
export class WingResolve implements Resolve<IWing> {
  constructor(private service: WingService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWing> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((wing: HttpResponse<Wing>) => {
          if (wing.body) {
            return of(wing.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Wing());
  }
}

export const wingRoute: Routes = [
  {
    path: '',
    component: WingComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wwHackathonTeam2App.wing.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WingDetailComponent,
    resolve: {
      wing: WingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wwHackathonTeam2App.wing.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WingUpdateComponent,
    resolve: {
      wing: WingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wwHackathonTeam2App.wing.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WingUpdateComponent,
    resolve: {
      wing: WingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wwHackathonTeam2App.wing.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
