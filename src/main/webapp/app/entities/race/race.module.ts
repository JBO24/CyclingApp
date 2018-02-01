import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CyclingAppSharedModule } from '../../shared';
import {
    RaceService,
    RacePopupService,
    RaceComponent,
    RaceDetailComponent,
    RaceDialogComponent,
    RacePopupComponent,
    RaceDeletePopupComponent,
    RaceDeleteDialogComponent,
    raceRoute,
    racePopupRoute,
} from './';

const ENTITY_STATES = [
    ...raceRoute,
    ...racePopupRoute,
];

@NgModule({
    imports: [
        CyclingAppSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RaceComponent,
        RaceDetailComponent,
        RaceDialogComponent,
        RaceDeleteDialogComponent,
        RacePopupComponent,
        RaceDeletePopupComponent,
    ],
    entryComponents: [
        RaceComponent,
        RaceDialogComponent,
        RacePopupComponent,
        RaceDeleteDialogComponent,
        RaceDeletePopupComponent,
    ],
    providers: [
        RaceService,
        RacePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CyclingAppRaceModule {}
