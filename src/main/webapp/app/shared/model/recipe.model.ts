import { INotes } from 'app/shared/model//notes.model';
import { IIngredient } from 'app/shared/model//ingredient.model';
import { ICategory } from 'app/shared/model//category.model';

export const enum Difficulty {
    EASY = 'EASY',
    MEDIUM = 'MEDIUM',
    HARD = 'HARD'
}

export interface IRecipe {
    id?: number;
    description?: string;
    prepTime?: number;
    cookTime?: number;
    servings?: number;
    source?: string;
    url?: string;
    directions?: string;
    imageContentType?: string;
    image?: any;
    difficulty?: Difficulty;
    notes?: INotes;
    ingredients?: IIngredient[];
    categories?: ICategory[];
}

export class Recipe implements IRecipe {
    constructor(
        public id?: number,
        public description?: string,
        public prepTime?: number,
        public cookTime?: number,
        public servings?: number,
        public source?: string,
        public url?: string,
        public directions?: string,
        public imageContentType?: string,
        public image?: any,
        public difficulty?: Difficulty,
        public notes?: INotes,
        public ingredients?: IIngredient[],
        public categories?: ICategory[]
    ) {}
}
