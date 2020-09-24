import { IFloor } from 'app/shared/model/floor.model';

export interface IBuilding {
  id: number;
  identifier: number;
  floors?: IFloor[];
}

export class Building implements IBuilding {
  constructor(public id: number, public identifier: number, public floors?: IFloor[]) {}
}
