import { Moment } from 'moment';

export interface IDailyReservation {
  id?: number;
  date?: Moment;
  deskgroupId?: number;

  deskgroup?: number;
  floor?: number;
  building?: string;
  wing?: string;
  userId?: number;
}

export class DailyReservation implements IDailyReservation {
  constructor(public id?: number, public date?: Moment, public deskgroupId?: number, public userId?: number) {}
}
