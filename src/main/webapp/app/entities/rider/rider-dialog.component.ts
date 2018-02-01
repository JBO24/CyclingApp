import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Rider } from './rider.model';
import { RiderPopupService } from './rider-popup.service';
import { RiderService } from './rider.service';
import { Team, TeamService } from '../team';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-rider-dialog',
    templateUrl: './rider-dialog.component.html'
})
export class RiderDialogComponent implements OnInit {

    rider: Rider;
    isSaving: boolean;

    teams: Team[];
    dateOfBirthDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private riderService: RiderService,
        private teamService: TeamService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.teamService.query()
            .subscribe((res: ResponseWrapper) => { this.teams = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rider.id !== undefined) {
            this.subscribeToSaveResponse(
                this.riderService.update(this.rider));
        } else {
            this.subscribeToSaveResponse(
                this.riderService.create(this.rider));
        }
    }

    private subscribeToSaveResponse(result: Observable<Rider>) {
        result.subscribe((res: Rider) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Rider) {
        this.eventManager.broadcast({ name: 'riderListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTeamById(index: number, item: Team) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-rider-popup',
    template: ''
})
export class RiderPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private riderPopupService: RiderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.riderPopupService
                    .open(RiderDialogComponent as Component, params['id']);
            } else {
                this.riderPopupService
                    .open(RiderDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
