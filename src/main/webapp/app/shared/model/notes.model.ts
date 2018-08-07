import { IRecipe } from 'app/shared/model//recipe.model';

export interface INotes {
    id?: number;
    recipeNotes?: string;
    recipe?: IRecipe;
}

export class Notes implements INotes {
    constructor(public id?: number, public recipeNotes?: string, public recipe?: IRecipe) {}
}
