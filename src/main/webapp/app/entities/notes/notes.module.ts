import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    NotesComponent,
    NotesDetailComponent,
    NotesUpdateComponent,
    NotesDeletePopupComponent,
    NotesDeleteDialogComponent,
    notesRoute,
    notesPopupRoute
} from './';

const ENTITY_STATES = [...notesRoute, ...notesPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [NotesComponent, NotesDetailComponent, NotesUpdateComponent, NotesDeleteDialogComponent, NotesDeletePopupComponent],
    entryComponents: [NotesComponent, NotesUpdateComponent, NotesDeleteDialogComponent, NotesDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationNotesModule {}
