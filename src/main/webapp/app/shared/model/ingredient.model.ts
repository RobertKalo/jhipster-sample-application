import { IUnitOfMeasure } from 'app/shared/model//unit-of-measure.model';
import { IRecipe } from 'app/shared/model//recipe.model';

export interface IIngredient {
    id?: number;
    description?: string;
    amount?: number;
    uom?: IUnitOfMeasure;
    recipe?: IRecipe;
}

export class Ingredient implements IIngredient {
    constructor(
        public id?: number,
        public description?: string,
        public amount?: number,
        public uom?: IUnitOfMeasure,
        public recipe?: IRecipe
    ) {}
}
