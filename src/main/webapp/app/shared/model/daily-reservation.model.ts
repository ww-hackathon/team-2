import { Moment } from 'moment';

export interface IDailyReservation {
  id?: number;
  date?: Moment;
  deskgroupId?: number;
  userId?: number;
}

export class DailyReservation implements IDailyReservation {
  constructor(public id?: number, public date?: Moment, public deskgroupId?: number, public userId?: number) {}
}
