import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Rider } from './rider.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RiderService {

    private resourceUrl =  SERVER_API_URL + 'api/riders';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(rider: Rider): Observable<Rider> {
        const copy = this.convert(rider);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(rider: Rider): Observable<Rider> {
        const copy = this.convert(rider);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Rider> {
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
     * Convert a returned JSON object to Rider.
     */
    private convertItemFromServer(json: any): Rider {
        const entity: Rider = Object.assign(new Rider(), json);
        entity.dateOfBirth = this.dateUtils
            .convertLocalDateFromServer(json.dateOfBirth);
        return entity;
    }

    /**
     * Convert a Rider to a JSON which can be sent to the server.
     */
    private convert(rider: Rider): Rider {
        const copy: Rider = Object.assign({}, rider);
        copy.dateOfBirth = this.dateUtils
            .convertLocalDateToServer(rider.dateOfBirth);
        return copy;
    }
}
