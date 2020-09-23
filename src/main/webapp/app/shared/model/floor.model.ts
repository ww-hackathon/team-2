import { IWing } from 'app/shared/model/wing.model';
import { FloorIdentifier } from 'app/shared/model/enumerations/floor-identifier.model';

export interface IFloor {
  id?: number;
  identifier?: FloorIdentifier;
  wings?: IWing[];
  buildingId?: number;
}

export class Floor implements IFloor {
  constructor(public id?: number, public identifier?: FloorIdentifier, public wings?: IWing[], public buildingId?: number) {}
}
