import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WwHackathonTeam2SharedModule } from 'app/shared/shared.module';
import { WingComponent } from './wing.component';
import { WingDetailComponent } from './wing-detail.component';
import { WingUpdateComponent } from './wing-update.component';
import { WingDeleteDialogComponent } from './wing-delete-dialog.component';
import { wingRoute } from './wing.route';

@NgModule({
  imports: [WwHackathonTeam2SharedModule, RouterModule.forChild(wingRoute)],
  declarations: [WingComponent, WingDetailComponent, WingUpdateComponent, WingDeleteDialogComponent],
  entryComponents: [WingDeleteDialogComponent],
})
export class WwHackathonTeam2WingModule {}
