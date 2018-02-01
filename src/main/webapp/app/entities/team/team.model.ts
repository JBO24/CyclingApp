import { BaseEntity } from './../../shared';

export class Team implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public amountOfVictories?: number,
        public yearFounded?: number,
        public teamManager?: string,
        public brandOfBicycle?: string,
        public riders?: BaseEntity[],
    ) {
    }
}
