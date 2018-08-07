import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    UnitOfMeasureComponent,
    UnitOfMeasureDetailComponent,
    UnitOfMeasureUpdateComponent,
    UnitOfMeasureDeletePopupComponent,
    UnitOfMeasureDeleteDialogComponent,
    unitOfMeasureRoute,
    unitOfMeasurePopupRoute
} from './';

const ENTITY_STATES = [...unitOfMeasureRoute, ...unitOfMeasurePopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UnitOfMeasureComponent,
        UnitOfMeasureDetailComponent,
        UnitOfMeasureUpdateComponent,
        UnitOfMeasureDeleteDialogComponent,
        UnitOfMeasureDeletePopupComponent
    ],
    entryComponents: [
        UnitOfMeasureComponent,
        UnitOfMeasureUpdateComponent,
        UnitOfMeasureDeleteDialogComponent,
        UnitOfMeasureDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationUnitOfMeasureModule {}
