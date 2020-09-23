export interface IDeskgroup {
  id?: number;
  seats?: number;
  identifier?: number;
  availableSeats?: number;
}

export class Deskgroup implements IDeskgroup {
  constructor(public id?: number, public seats?: number, public identifier?: number, public availableSeats?: number) {}
}
