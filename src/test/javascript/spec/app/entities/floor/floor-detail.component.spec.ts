import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WwHackathonTeam2TestModule } from '../../../test.module';
import { FloorDetailComponent } from 'app/entities/floor/floor-detail.component';
import { Floor } from 'app/shared/model/floor.model';

describe('Component Tests', () => {
  describe('Floor Management Detail Component', () => {
    let comp: FloorDetailComponent;
    let fixture: ComponentFixture<FloorDetailComponent>;
    const route = ({ data: of({ floor: new Floor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WwHackathonTeam2TestModule],
        declarations: [FloorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FloorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FloorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load floor on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.floor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
