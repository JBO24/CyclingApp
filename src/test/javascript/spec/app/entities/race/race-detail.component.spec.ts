/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { CyclingAppTestModule } from '../../../test.module';
import { RaceDetailComponent } from '../../../../../../main/webapp/app/entities/race/race-detail.component';
import { RaceService } from '../../../../../../main/webapp/app/entities/race/race.service';
import { Race } from '../../../../../../main/webapp/app/entities/race/race.model';

describe('Component Tests', () => {

    describe('Race Management Detail Component', () => {
        let comp: RaceDetailComponent;
        let fixture: ComponentFixture<RaceDetailComponent>;
        let service: RaceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CyclingAppTestModule],
                declarations: [RaceDetailComponent],
                providers: [
                    RaceService
                ]
            })
            .overrideTemplate(RaceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RaceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RaceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Race(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.race).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
