import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WwHackathonTeam2TestModule } from '../../../test.module';
import { FloorComponent } from 'app/entities/floor/floor.component';
import { FloorService } from 'app/entities/floor/floor.service';
import { Floor } from 'app/shared/model/floor.model';

describe('Component Tests', () => {
  describe('Floor Management Component', () => {
    let comp: FloorComponent;
    let fixture: ComponentFixture<FloorComponent>;
    let service: FloorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WwHackathonTeam2TestModule],
        declarations: [FloorComponent],
      })
        .overrideTemplate(FloorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FloorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FloorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Floor(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.floors && comp.floors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
