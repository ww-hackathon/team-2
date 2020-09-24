import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WwHackathonTeam2TestModule } from '../../../test.module';
import { BuildingComponent } from 'app/entities/building/building.component';
import { BuildingService } from 'app/entities/building/building.service';
import { Building } from 'app/shared/model/building.model';

describe('Component Tests', () => {
  describe('Building Management Component', () => {
    let comp: BuildingComponent;
    let fixture: ComponentFixture<BuildingComponent>;
    let service: BuildingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WwHackathonTeam2TestModule],
        declarations: [BuildingComponent],
      })
        .overrideTemplate(BuildingComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BuildingComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BuildingService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Building(123, 11)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.buildings && comp.buildings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
