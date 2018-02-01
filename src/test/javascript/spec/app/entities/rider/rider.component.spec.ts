/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { CyclingAppTestModule } from '../../../test.module';
import { RiderComponent } from '../../../../../../main/webapp/app/entities/rider/rider.component';
import { RiderService } from '../../../../../../main/webapp/app/entities/rider/rider.service';
import { Rider } from '../../../../../../main/webapp/app/entities/rider/rider.model';

describe('Component Tests', () => {

    describe('Rider Management Component', () => {
        let comp: RiderComponent;
        let fixture: ComponentFixture<RiderComponent>;
        let service: RiderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CyclingAppTestModule],
                declarations: [RiderComponent],
                providers: [
                    RiderService
                ]
            })
            .overrideTemplate(RiderComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RiderComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RiderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Rider(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.riders[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
