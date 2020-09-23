import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDeskgroup } from 'app/shared/model/deskgroup.model';

type EntityResponseType = HttpResponse<IDeskgroup>;
type EntityArrayResponseType = HttpResponse<IDeskgroup[]>;

@Injectable({ providedIn: 'root' })
export class DeskgroupService {
  public resourceUrl = SERVER_API_URL + 'api/deskgroups';

  constructor(protected http: HttpClient) {}

  create(deskgroup: IDeskgroup): Observable<EntityResponseType> {
    return this.http.post<IDeskgroup>(this.resourceUrl, deskgroup, { observe: 'response' });
  }

  update(deskgroup: IDeskgroup): Observable<EntityResponseType> {
    return this.http.put<IDeskgroup>(this.resourceUrl, deskgroup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDeskgroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDeskgroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
