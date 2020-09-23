import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WwHackathonTeam2SharedModule } from 'app/shared/shared.module';
import { DeskgroupComponent } from './deskgroup.component';
import { DeskgroupDetailComponent } from './deskgroup-detail.component';
import { DeskgroupUpdateComponent } from './deskgroup-update.component';
import { DeskgroupDeleteDialogComponent } from './deskgroup-delete-dialog.component';
import { deskgroupRoute } from './deskgroup.route';

@NgModule({
  imports: [WwHackathonTeam2SharedModule, RouterModule.forChild(deskgroupRoute)],
  declarations: [DeskgroupComponent, DeskgroupDetailComponent, DeskgroupUpdateComponent, DeskgroupDeleteDialogComponent],
  entryComponents: [DeskgroupDeleteDialogComponent],
})
export class WwHackathonTeam2DeskgroupModule {}
