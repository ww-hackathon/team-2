import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWing } from 'app/shared/model/wing.model';
import { WingService } from './wing.service';

@Component({
  templateUrl: './wing-delete-dialog.component.html',
})
export class WingDeleteDialogComponent {
  wing?: IWing;

  constructor(protected wingService: WingService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.wingService.delete(id).subscribe(() => {
      this.eventManager.broadcast('wingListModification');
      this.activeModal.close();
    });
  }
}
