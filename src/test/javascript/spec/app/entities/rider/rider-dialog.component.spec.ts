/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CyclingAppTestModule } from '../../../test.module';
import { RiderDialogComponent } from '../../../../../../main/webapp/app/entities/rider/rider-dialog.component';
import { RiderService } from '../../../../../../main/webapp/app/entities/rider/rider.service';
import { Rider } from '../../../../../../main/webapp/app/entities/rider/rider.model';
import { TeamService } from '../../../../../../main/webapp/app/entities/team';

describe('Component Tests', () => {

    describe('Rider Management Dialog Component', () => {
        let comp: RiderDialogComponent;
        let fixture: ComponentFixture<RiderDialogComponent>;
        let service: RiderService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CyclingAppTestModule],
                declarations: [RiderDialogComponent],
                providers: [
                    TeamService,
                    RiderService
                ]
            })
            .overrideTemplate(RiderDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RiderDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RiderService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Rider(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.rider = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'riderListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Rider();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.rider = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'riderListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
