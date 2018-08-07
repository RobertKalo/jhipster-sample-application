import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterSampleApplicationRecipeModule } from './recipe/recipe.module';
import { JhipsterSampleApplicationIngredientModule } from './ingredient/ingredient.module';
import { JhipsterSampleApplicationNotesModule } from './notes/notes.module';
import { JhipsterSampleApplicationCategoryModule } from './category/category.module';
import { JhipsterSampleApplicationUnitOfMeasureModule } from './unit-of-measure/unit-of-measure.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JhipsterSampleApplicationRecipeModule,
        JhipsterSampleApplicationIngredientModule,
        JhipsterSampleApplicationNotesModule,
        JhipsterSampleApplicationCategoryModule,
        JhipsterSampleApplicationUnitOfMeasureModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
