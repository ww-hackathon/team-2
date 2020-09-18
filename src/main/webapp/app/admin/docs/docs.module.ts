import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { WwHackathonTeam2SharedModule } from 'app/shared/shared.module';

import { DocsComponent } from './docs.component';

import { docsRoute } from './docs.route';

@NgModule({
  imports: [WwHackathonTeam2SharedModule, RouterModule.forChild([docsRoute])],
  declarations: [DocsComponent],
})
export class DocsModule {}
