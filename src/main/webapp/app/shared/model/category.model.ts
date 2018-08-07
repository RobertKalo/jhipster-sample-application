import { IRecipe } from 'app/shared/model//recipe.model';

export interface ICategory {
    id?: number;
    description?: string;
    recipes?: IRecipe[];
}

export class Category implements ICategory {
    constructor(public id?: number, public description?: string, public recipes?: IRecipe[]) {}
}
