import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WwHackathonTeam2TestModule } from '../../../test.module';
import { WingDetailComponent } from 'app/entities/wing/wing-detail.component';
import { Wing } from 'app/shared/model/wing.model';

describe('Component Tests', () => {
  describe('Wing Management Detail Component', () => {
    let comp: WingDetailComponent;
    let fixture: ComponentFixture<WingDetailComponent>;
    const route = ({ data: of({ wing: new Wing(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WwHackathonTeam2TestModule],
        declarations: [WingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load wing on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.wing).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
