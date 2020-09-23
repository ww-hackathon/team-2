import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WwHackathonTeam2TestModule } from '../../../test.module';
import { DailyReservationUpdateComponent } from 'app/entities/daily-reservation/daily-reservation-update.component';
import { DailyReservationService } from 'app/entities/daily-reservation/daily-reservation.service';
import { DailyReservation } from 'app/shared/model/daily-reservation.model';

describe('Component Tests', () => {
  describe('DailyReservation Management Update Component', () => {
    let comp: DailyReservationUpdateComponent;
    let fixture: ComponentFixture<DailyReservationUpdateComponent>;
    let service: DailyReservationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WwHackathonTeam2TestModule],
        declarations: [DailyReservationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DailyReservationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DailyReservationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DailyReservationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DailyReservation(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DailyReservation();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
