import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Notes } from 'app/shared/model/notes.model';
import { NotesService } from './notes.service';
import { NotesComponent } from './notes.component';
import { NotesDetailComponent } from './notes-detail.component';
import { NotesUpdateComponent } from './notes-update.component';
import { NotesDeletePopupComponent } from './notes-delete-dialog.component';
import { INotes } from 'app/shared/model/notes.model';

@Injectable({ providedIn: 'root' })
export class NotesResolve implements Resolve<INotes> {
    constructor(private service: NotesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((notes: HttpResponse<Notes>) => notes.body));
        }
        return of(new Notes());
    }
}

export const notesRoute: Routes = [
    {
        path: 'notes',
        component: NotesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Notes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'notes/:id/view',
        component: NotesDetailComponent,
        resolve: {
            notes: NotesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Notes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'notes/new',
        component: NotesUpdateComponent,
        resolve: {
            notes: NotesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Notes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'notes/:id/edit',
        component: NotesUpdateComponent,
        resolve: {
            notes: NotesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Notes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const notesPopupRoute: Routes = [
    {
        path: 'notes/:id/delete',
        component: NotesDeletePopupComponent,
        resolve: {
            notes: NotesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Notes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
