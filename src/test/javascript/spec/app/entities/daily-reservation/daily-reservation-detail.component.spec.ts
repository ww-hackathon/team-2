import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WwHackathonTeam2TestModule } from '../../../test.module';
import { DailyReservationDetailComponent } from 'app/entities/daily-reservation/daily-reservation-detail.component';
import { DailyReservation } from 'app/shared/model/daily-reservation.model';

describe('Component Tests', () => {
  describe('DailyReservation Management Detail Component', () => {
    let comp: DailyReservationDetailComponent;
    let fixture: ComponentFixture<DailyReservationDetailComponent>;
    const route = ({ data: of({ dailyReservation: new DailyReservation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WwHackathonTeam2TestModule],
        declarations: [DailyReservationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DailyReservationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DailyReservationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dailyReservation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dailyReservation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
