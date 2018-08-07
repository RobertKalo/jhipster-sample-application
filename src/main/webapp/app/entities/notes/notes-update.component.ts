import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { INotes } from 'app/shared/model/notes.model';
import { NotesService } from './notes.service';
import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe';

@Component({
    selector: 'jhi-notes-update',
    templateUrl: './notes-update.component.html'
})
export class NotesUpdateComponent implements OnInit {
    private _notes: INotes;
    isSaving: boolean;

    recipes: IRecipe[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private notesService: NotesService,
        private recipeService: RecipeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ notes }) => {
            this.notes = notes;
        });
        this.recipeService.query().subscribe(
            (res: HttpResponse<IRecipe[]>) => {
                this.recipes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.notes.id !== undefined) {
            this.subscribeToSaveResponse(this.notesService.update(this.notes));
        } else {
            this.subscribeToSaveResponse(this.notesService.create(this.notes));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<INotes>>) {
        result.subscribe((res: HttpResponse<INotes>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRecipeById(index: number, item: IRecipe) {
        return item.id;
    }
    get notes() {
        return this._notes;
    }

    set notes(notes: INotes) {
        this._notes = notes;
    }
}
