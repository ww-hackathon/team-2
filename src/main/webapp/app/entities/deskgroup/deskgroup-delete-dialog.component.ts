import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeskgroup } from 'app/shared/model/deskgroup.model';
import { DeskgroupService } from './deskgroup.service';

@Component({
  templateUrl: './deskgroup-delete-dialog.component.html',
})
export class DeskgroupDeleteDialogComponent {
  deskgroup?: IDeskgroup;

  constructor(protected deskgroupService: DeskgroupService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.deskgroupService.delete(id).subscribe(() => {
      this.eventManager.broadcast('deskgroupListModification');
      this.activeModal.close();
    });
  }
}
