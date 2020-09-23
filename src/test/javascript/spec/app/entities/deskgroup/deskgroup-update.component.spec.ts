import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WwHackathonTeam2TestModule } from '../../../test.module';
import { DeskgroupUpdateComponent } from 'app/entities/deskgroup/deskgroup-update.component';
import { DeskgroupService } from 'app/entities/deskgroup/deskgroup.service';
import { Deskgroup } from 'app/shared/model/deskgroup.model';

describe('Component Tests', () => {
  describe('Deskgroup Management Update Component', () => {
    let comp: DeskgroupUpdateComponent;
    let fixture: ComponentFixture<DeskgroupUpdateComponent>;
    let service: DeskgroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WwHackathonTeam2TestModule],
        declarations: [DeskgroupUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DeskgroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeskgroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeskgroupService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Deskgroup(123);
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
        const entity = new Deskgroup();
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
