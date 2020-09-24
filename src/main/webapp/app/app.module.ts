import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import './vendor';
import { WwHackathonTeam2SharedModule } from 'app/shared/shared.module';
import { WwHackathonTeam2CoreModule } from 'app/core/core.module';
import { WwHackathonTeam2AppRoutingModule } from './app-routing.module';
import { WwHackathonTeam2HomeModule } from './home/home.module';
import { WwHackathonTeam2EntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserAnimationsModule,
    WwHackathonTeam2SharedModule,
    WwHackathonTeam2CoreModule,
    WwHackathonTeam2HomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    WwHackathonTeam2EntityModule,
    WwHackathonTeam2AppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class WwHackathonTeam2AppModule {}
