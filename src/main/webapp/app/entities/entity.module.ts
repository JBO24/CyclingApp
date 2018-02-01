import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CyclingAppTeamModule } from './team/team.module';
import { CyclingAppRiderModule } from './rider/rider.module';
import { CyclingAppRaceModule } from './race/race.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CyclingAppTeamModule,
        CyclingAppRiderModule,
        CyclingAppRaceModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CyclingAppEntityModule {}
