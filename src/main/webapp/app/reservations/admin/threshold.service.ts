import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from '../../../app/app.constants';
import { createRequestOption } from '../../../app/shared/util/request-util';

type EntityResponseType = HttpResponse<any>;
type EntityArrayResponseType = HttpResponse<any[]>;

@Injectable({ providedIn: 'root' })
export class ThresholdService {
  public resourceUrl = SERVER_API_URL + 'api/admin/threshold';

  constructor(protected http: HttpClient) {}

  update(threshold: any): Observable<EntityResponseType> {
    return this.http.put<any>(`${this.resourceUrl}/?${threshold}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<any>(this.resourceUrl, { params: options, observe: 'response' });
  }
}
