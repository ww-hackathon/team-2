import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ThresholdService } from './threshold.service';
/* eslint-disable */

@Component({
  selector: 'jhi-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss'],
})
export class ReservationsAdminComponent implements OnInit {
  angForm: FormGroup = this.fb.group({
    threshold: [50, Validators.required],
  });

  constructor(protected thresholdService: ThresholdService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.thresholdService.query().subscribe((res: any) => {
      this.angForm.setValue({ threshold: res.body || 50 });
    });
  }

  onSubmit(): void {
    this.thresholdService.update(this.angForm.get('threshold').value).subscribe((res: any) => {});
  }
}
