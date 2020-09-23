import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeskgroup } from 'app/shared/model/deskgroup.model';

@Component({
  selector: 'jhi-deskgroup-detail',
  templateUrl: './deskgroup-detail.component.html',
})
export class DeskgroupDetailComponent implements OnInit {
  deskgroup: IDeskgroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deskgroup }) => (this.deskgroup = deskgroup));
  }

  previousState(): void {
    window.history.back();
  }
}
