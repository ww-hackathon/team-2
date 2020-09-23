import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWing } from 'app/shared/model/wing.model';

type EntityResponseType = HttpResponse<IWing>;
type EntityArrayResponseType = HttpResponse<IWing[]>;

@Injectable({ providedIn: 'root' })
export class WingService {
  public resourceUrl = SERVER_API_URL + 'api/wings';

  constructor(protected http: HttpClient) {}

  create(wing: IWing): Observable<EntityResponseType> {
    return this.http.post<IWing>(this.resourceUrl, wing, { observe: 'response' });
  }

  update(wing: IWing): Observable<EntityResponseType> {
    return this.http.put<IWing>(this.resourceUrl, wing, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWing>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWing[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
