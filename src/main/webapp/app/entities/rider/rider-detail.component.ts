import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Rider } from './rider.model';
import { RiderService } from './rider.service';

@Component({
    selector: 'jhi-rider-detail',
    templateUrl: './rider-detail.component.html'
})
export class RiderDetailComponent implements OnInit, OnDestroy {

    rider: Rider;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private riderService: RiderService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRiders();
    }

    load(id) {
        this.riderService.find(id).subscribe((rider) => {
            this.rider = rider;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRiders() {
        this.eventSubscriber = this.eventManager.subscribe(
            'riderListModification',
            (response) => this.load(this.rider.id)
        );
    }
}
