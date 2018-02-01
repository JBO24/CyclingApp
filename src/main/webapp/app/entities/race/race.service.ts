import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Race } from './race.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RaceService {

    private resourceUrl =  SERVER_API_URL + 'api/races';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(race: Race): Observable<Race> {
        const copy = this.convert(race);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(race: Race): Observable<Race> {
        const copy = this.convert(race);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Race> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Race.
     */
    private convertItemFromServer(json: any): Race {
        const entity: Race = Object.assign(new Race(), json);
        entity.dateOfRace = this.dateUtils
            .convertLocalDateFromServer(json.dateOfRace);
        return entity;
    }

    /**
     * Convert a Race to a JSON which can be sent to the server.
     */
    private convert(race: Race): Race {
        const copy: Race = Object.assign({}, race);
        copy.dateOfRace = this.dateUtils
            .convertLocalDateToServer(race.dateOfRace);
        return copy;
    }
}
