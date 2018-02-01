/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { CyclingAppTestModule } from '../../../test.module';
import { RiderDetailComponent } from '../../../../../../main/webapp/app/entities/rider/rider-detail.component';
import { RiderService } from '../../../../../../main/webapp/app/entities/rider/rider.service';
import { Rider } from '../../../../../../main/webapp/app/entities/rider/rider.model';

describe('Component Tests', () => {

    describe('Rider Management Detail Component', () => {
        let comp: RiderDetailComponent;
        let fixture: ComponentFixture<RiderDetailComponent>;
        let service: RiderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CyclingAppTestModule],
                declarations: [RiderDetailComponent],
                providers: [
                    RiderService
                ]
            })
            .overrideTemplate(RiderDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RiderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RiderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Rider(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.rider).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
