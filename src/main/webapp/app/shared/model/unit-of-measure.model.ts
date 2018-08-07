export interface IUnitOfMeasure {
    id?: number;
    description?: string;
}

export class UnitOfMeasure implements IUnitOfMeasure {
    constructor(public id?: number, public description?: string) {}
}
