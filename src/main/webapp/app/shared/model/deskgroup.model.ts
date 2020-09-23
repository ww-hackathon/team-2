export interface IDeskgroup {
  id?: number;
  seats?: number;
  identifier?: number;
  wingId?: number;
}

export class Deskgroup implements IDeskgroup {
  constructor(public id?: number, public seats?: number, public identifier?: number, public wingId?: number) {}
}
