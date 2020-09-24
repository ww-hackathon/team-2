import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { BuildingService } from 'app/entities/building/building.service';
import { IBuilding } from 'app/shared/model/building.model';
import { map } from 'rxjs/operators';
/* eslint-disable */

@Component({
  selector: 'jhi-new-reservation',
  templateUrl: './new-reservation.component.html',
  styleUrls: ['./new-reservation.component.scss'],
})
export class NewReservationComponent implements OnInit {
  form = this.fb.group({
    startDate: this.fb.control(new Date()),
    endDate: this.fb.control(''),
    building: this.fb.control(''),
  });

  buildings$ = this.buildingService.query().pipe(
    map((res: any) => {
      const buildings: IBuilding[] = res.body as IBuilding[];
      return buildings.sort((a, b) => {
        if (a.identifier > b.identifier) {
          return 1;
        }
        if (a.identifier < b.identifier) {
          return -1;
        }

        return 0;
      });
    })
  );

  constructor(private fb: FormBuilder, private buildingService: BuildingService) {}

  ngOnInit(): void {}

  onSubmit() {
    console.log(this.form.value);
  }
}
