/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CyclingAppTestModule } from '../../../test.module';
import { RaceDialogComponent } from '../../../../../../main/webapp/app/entities/race/race-dialog.component';
import { RaceService } from '../../../../../../main/webapp/app/entities/race/race.service';
import { Race } from '../../../../../../main/webapp/app/entities/race/race.model';

describe('Component Tests', () => {

    describe('Race Management Dialog Component', () => {
        let comp: RaceDialogComponent;
        let fixture: ComponentFixture<RaceDialogComponent>;
        let service: RaceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CyclingAppTestModule],
                declarations: [RaceDialogComponent],
                providers: [
                    RaceService
                ]
            })
            .overrideTemplate(RaceDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RaceDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RaceService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Race(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.race = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'raceListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Race();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.race = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'raceListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
