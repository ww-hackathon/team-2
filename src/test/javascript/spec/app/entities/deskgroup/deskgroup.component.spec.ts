import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WwHackathonTeam2TestModule } from '../../../test.module';
import { DeskgroupComponent } from 'app/entities/deskgroup/deskgroup.component';
import { DeskgroupService } from 'app/entities/deskgroup/deskgroup.service';
import { Deskgroup } from 'app/shared/model/deskgroup.model';

describe('Component Tests', () => {
  describe('Deskgroup Management Component', () => {
    let comp: DeskgroupComponent;
    let fixture: ComponentFixture<DeskgroupComponent>;
    let service: DeskgroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WwHackathonTeam2TestModule],
        declarations: [DeskgroupComponent],
      })
        .overrideTemplate(DeskgroupComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeskgroupComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeskgroupService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Deskgroup(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.deskgroups && comp.deskgroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
