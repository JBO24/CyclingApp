import { BaseEntity } from './../../shared';

export class Rider implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public dateOfBirth?: any,
        public amountOfVictories?: number,
        public length?: number,
        public weight?: number,
        public team?: BaseEntity
    ) {
    }
}
