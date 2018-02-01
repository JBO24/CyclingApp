import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Rider } from './rider.model';
import { RiderPopupService } from './rider-popup.service';
import { RiderService } from './rider.service';

@Component({
    selector: 'jhi-rider-delete-dialog',
    templateUrl: './rider-delete-dialog.component.html'
})
export class RiderDeleteDialogComponent {

    rider: Rider;

    constructor(
        private riderService: RiderService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.riderService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'riderListModification',
                content: 'Deleted an rider'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rider-delete-popup',
    template: ''
})
export class RiderDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private riderPopupService: RiderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.riderPopupService
                .open(RiderDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
