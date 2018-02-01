/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CyclingAppTestModule } from '../../../test.module';
import { RiderDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/rider/rider-delete-dialog.component';
import { RiderService } from '../../../../../../main/webapp/app/entities/rider/rider.service';

describe('Component Tests', () => {

    describe('Rider Management Delete Component', () => {
        let comp: RiderDeleteDialogComponent;
        let fixture: ComponentFixture<RiderDeleteDialogComponent>;
        let service: RiderService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CyclingAppTestModule],
                declarations: [RiderDeleteDialogComponent],
                providers: [
                    RiderService
                ]
            })
            .overrideTemplate(RiderDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RiderDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RiderService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
