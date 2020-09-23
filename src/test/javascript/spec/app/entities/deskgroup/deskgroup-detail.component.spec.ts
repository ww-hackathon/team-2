import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WwHackathonTeam2TestModule } from '../../../test.module';
import { DeskgroupDetailComponent } from 'app/entities/deskgroup/deskgroup-detail.component';
import { Deskgroup } from 'app/shared/model/deskgroup.model';

describe('Component Tests', () => {
  describe('Deskgroup Management Detail Component', () => {
    let comp: DeskgroupDetailComponent;
    let fixture: ComponentFixture<DeskgroupDetailComponent>;
    const route = ({ data: of({ deskgroup: new Deskgroup(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WwHackathonTeam2TestModule],
        declarations: [DeskgroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DeskgroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeskgroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load deskgroup on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.deskgroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
