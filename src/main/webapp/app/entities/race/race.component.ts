import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Race } from './race.model';
import { RaceService } from './race.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-race',
    templateUrl: './race.component.html'
})
export class RaceComponent implements OnInit, OnDestroy {
races: Race[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private raceService: RaceService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.raceService.query().subscribe(
            (res: ResponseWrapper) => {
                this.races = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRaces();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Race) {
        return item.id;
    }
    registerChangeInRaces() {
        this.eventSubscriber = this.eventManager.subscribe('raceListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
