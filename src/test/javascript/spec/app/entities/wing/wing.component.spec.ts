import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WwHackathonTeam2TestModule } from '../../../test.module';
import { WingComponent } from 'app/entities/wing/wing.component';
import { WingService } from 'app/entities/wing/wing.service';
import { Wing } from 'app/shared/model/wing.model';

describe('Component Tests', () => {
  describe('Wing Management Component', () => {
    let comp: WingComponent;
    let fixture: ComponentFixture<WingComponent>;
    let service: WingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WwHackathonTeam2TestModule],
        declarations: [WingComponent],
      })
        .overrideTemplate(WingComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WingComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WingService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Wing(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.wings && comp.wings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
