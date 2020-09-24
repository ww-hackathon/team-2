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
  today = new Date();
  maxDate = new Date(new Date().setDate(this.today.getDate() + 7));

  form = this.fb.group({
    startDate: this.fb.control(this.today),
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

  dateFilter(date: Date) {
    const notSatSun = date.getUTCDay() != 5 && date.getUTCDay() != 6;
    return notSatSun;
  }
}
