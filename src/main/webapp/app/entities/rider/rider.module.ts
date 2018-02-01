import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CyclingAppSharedModule } from '../../shared';
import {
    RiderService,
    RiderPopupService,
    RiderComponent,
    RiderDetailComponent,
    RiderDialogComponent,
    RiderPopupComponent,
    RiderDeletePopupComponent,
    RiderDeleteDialogComponent,
    riderRoute,
    riderPopupRoute,
} from './';

const ENTITY_STATES = [
    ...riderRoute,
    ...riderPopupRoute,
];

@NgModule({
    imports: [
        CyclingAppSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RiderComponent,
        RiderDetailComponent,
        RiderDialogComponent,
        RiderDeleteDialogComponent,
        RiderPopupComponent,
        RiderDeletePopupComponent,
    ],
    entryComponents: [
        RiderComponent,
        RiderDialogComponent,
        RiderPopupComponent,
        RiderDeleteDialogComponent,
        RiderDeletePopupComponent,
    ],
    providers: [
        RiderService,
        RiderPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CyclingAppRiderModule {}
