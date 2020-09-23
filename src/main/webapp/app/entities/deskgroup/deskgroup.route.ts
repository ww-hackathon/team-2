import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDeskgroup, Deskgroup } from 'app/shared/model/deskgroup.model';
import { DeskgroupService } from './deskgroup.service';
import { DeskgroupComponent } from './deskgroup.component';
import { DeskgroupDetailComponent } from './deskgroup-detail.component';
import { DeskgroupUpdateComponent } from './deskgroup-update.component';

@Injectable({ providedIn: 'root' })
export class DeskgroupResolve implements Resolve<IDeskgroup> {
  constructor(private service: DeskgroupService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeskgroup> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((deskgroup: HttpResponse<Deskgroup>) => {
          if (deskgroup.body) {
            return of(deskgroup.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Deskgroup());
  }
}

export const deskgroupRoute: Routes = [
  {
    path: '',
    component: DeskgroupComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wwHackathonTeam2App.deskgroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DeskgroupDetailComponent,
    resolve: {
      deskgroup: DeskgroupResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wwHackathonTeam2App.deskgroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DeskgroupUpdateComponent,
    resolve: {
      deskgroup: DeskgroupResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wwHackathonTeam2App.deskgroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DeskgroupUpdateComponent,
    resolve: {
      deskgroup: DeskgroupResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wwHackathonTeam2App.deskgroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
