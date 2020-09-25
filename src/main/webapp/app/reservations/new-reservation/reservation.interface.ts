import { IBuilding } from '../../shared/model/building.model';
import { IDeskgroup } from '../../shared/model/deskgroup.model';
import { IWing } from '../../shared/model/wing.model';
import { IFloor } from '../../shared/model/floor.model';

export interface Reservation {
  buildingDTO: IBuilding;
  deskgroupDTO: IDeskgroup;
  floorDTO: IFloor;
  wingDTO: IWing;
}
