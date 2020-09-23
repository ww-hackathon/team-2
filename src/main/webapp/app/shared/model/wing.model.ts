import { IDeskgroup } from 'app/shared/model/deskgroup.model';
import { WingIdentifier } from 'app/shared/model/enumerations/wing-identifier.model';

export interface IWing {
  id?: number;
  identifier?: WingIdentifier;
  deskgroups?: IDeskgroup[];
  floorId?: number;
}

export class Wing implements IWing {
  constructor(public id?: number, public identifier?: WingIdentifier, public deskgroups?: IDeskgroup[], public floorId?: number) {}
}
