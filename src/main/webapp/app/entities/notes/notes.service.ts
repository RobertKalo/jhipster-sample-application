import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INotes } from 'app/shared/model/notes.model';

type EntityResponseType = HttpResponse<INotes>;
type EntityArrayResponseType = HttpResponse<INotes[]>;

@Injectable({ providedIn: 'root' })
export class NotesService {
    private resourceUrl = SERVER_API_URL + 'api/notes';

    constructor(private http: HttpClient) {}

    create(notes: INotes): Observable<EntityResponseType> {
        return this.http.post<INotes>(this.resourceUrl, notes, { observe: 'response' });
    }

    update(notes: INotes): Observable<EntityResponseType> {
        return this.http.put<INotes>(this.resourceUrl, notes, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<INotes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<INotes[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
