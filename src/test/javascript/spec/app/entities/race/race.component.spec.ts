/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { CyclingAppTestModule } from '../../../test.module';
import { RaceComponent } from '../../../../../../main/webapp/app/entities/race/race.component';
import { RaceService } from '../../../../../../main/webapp/app/entities/race/race.service';
import { Race } from '../../../../../../main/webapp/app/entities/race/race.model';

describe('Component Tests', () => {

    describe('Race Management Component', () => {
        let comp: RaceComponent;
        let fixture: ComponentFixture<RaceComponent>;
        let service: RaceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CyclingAppTestModule],
                declarations: [RaceComponent],
                providers: [
                    RaceService
                ]
            })
            .overrideTemplate(RaceComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RaceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RaceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Race(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.races[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
