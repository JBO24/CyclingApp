import { BaseEntity } from './../../shared';

export const enum TypeOfRace {
    'CLASSIC_COBBLES',
    'ONE_WEEK_STAGE',
    'GRAND_TOUR',
    'CLASSIC_HILL',
    'SHORT_STAGE'
}

export class Race implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public nickname?: string,
        public dateOfRace?: any,
        public amountOfDays?: number,
        public typeOfRace?: TypeOfRace,
        public yearOfFirstRace?: string,
        public lastWinner?: string,
    ) {
    }
}
