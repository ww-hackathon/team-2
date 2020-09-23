import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WwHackathonTeam2TestModule } from '../../../test.module';
import { WingUpdateComponent } from 'app/entities/wing/wing-update.component';
import { WingService } from 'app/entities/wing/wing.service';
import { Wing } from 'app/shared/model/wing.model';

describe('Component Tests', () => {
  describe('Wing Management Update Component', () => {
    let comp: WingUpdateComponent;
    let fixture: ComponentFixture<WingUpdateComponent>;
    let service: WingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WwHackathonTeam2TestModule],
        declarations: [WingUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Wing(123);
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
        const entity = new Wing();
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
