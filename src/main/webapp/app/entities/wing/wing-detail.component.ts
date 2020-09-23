import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWing } from 'app/shared/model/wing.model';

@Component({
  selector: 'jhi-wing-detail',
  templateUrl: './wing-detail.component.html',
})
export class WingDetailComponent implements OnInit {
  wing: IWing | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wing }) => (this.wing = wing));
  }

  previousState(): void {
    window.history.back();
  }
}
