import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WwHackathonTeam2TestModule } from '../../../test.module';
import { DailyReservationComponent } from 'app/entities/daily-reservation/daily-reservation.component';
import { DailyReservationService } from 'app/entities/daily-reservation/daily-reservation.service';
import { DailyReservation } from 'app/shared/model/daily-reservation.model';

describe('Component Tests', () => {
  describe('DailyReservation Management Component', () => {
    let comp: DailyReservationComponent;
    let fixture: ComponentFixture<DailyReservationComponent>;
    let service: DailyReservationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WwHackathonTeam2TestModule],
        declarations: [DailyReservationComponent],
      })
        .overrideTemplate(DailyReservationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DailyReservationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DailyReservationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DailyReservation(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dailyReservations && comp.dailyReservations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
