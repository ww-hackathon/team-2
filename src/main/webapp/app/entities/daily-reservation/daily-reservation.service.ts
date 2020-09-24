import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDailyReservation } from 'app/shared/model/daily-reservation.model';

type EntityResponseType = HttpResponse<IDailyReservation>;
type EntityArrayResponseType = HttpResponse<IDailyReservation[]>;

@Injectable({ providedIn: 'root' })
export class DailyReservationService {
  public resourceUrl = SERVER_API_URL + 'api/daily-reservations';

  constructor(protected http: HttpClient) {}

  create(dailyReservation: IDailyReservation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dailyReservation);
    return this.http
      .post<IDailyReservation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dailyReservation: IDailyReservation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dailyReservation);
    return this.http
      .put<IDailyReservation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDailyReservation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDailyReservation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(dailyReservation: IDailyReservation): IDailyReservation {
    const copy: IDailyReservation = Object.assign({}, dailyReservation, {
      date: dailyReservation.date && dailyReservation.date.isValid() ? dailyReservation.date.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dailyReservation: IDailyReservation) => {
        dailyReservation.date = dailyReservation.date ? moment(dailyReservation.date) : undefined;
      });
    }
    return res;
  }

  getDummyAvailableReservations(): Observable<IDailyReservation[]> {
    return of([
      {
        id: 1,
        building: '11',
        floor: 0,
        wing: 'A',
        deskgroup: 1,
      },
      {
        id: 2,
        building: '11',
        floor: 1,
        wing: 'B',
        deskgroup: 2,
      },
    ]);
  }
}
