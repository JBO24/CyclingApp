import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Rider } from './rider.model';
import { RiderService } from './rider.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-rider',
    templateUrl: './rider.component.html'
})
export class RiderComponent implements OnInit, OnDestroy {
riders: Rider[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private riderService: RiderService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.riderService.query().subscribe(
            (res: ResponseWrapper) => {
                this.riders = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRiders();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Rider) {
        return item.id;
    }
    registerChangeInRiders() {
        this.eventSubscriber = this.eventManager.subscribe('riderListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
