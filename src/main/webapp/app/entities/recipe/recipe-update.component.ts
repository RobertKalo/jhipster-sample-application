import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from './recipe.service';
import { INotes } from 'app/shared/model/notes.model';
import { NotesService } from 'app/entities/notes';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';

@Component({
    selector: 'jhi-recipe-update',
    templateUrl: './recipe-update.component.html'
})
export class RecipeUpdateComponent implements OnInit {
    private _recipe: IRecipe;
    isSaving: boolean;

    notes: INotes[];

    categories: ICategory[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private recipeService: RecipeService,
        private notesService: NotesService,
        private categoryService: CategoryService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ recipe }) => {
            this.recipe = recipe;
        });
        this.notesService.query({ filter: 'recipe-is-null' }).subscribe(
            (res: HttpResponse<INotes[]>) => {
                if (!this.recipe.notes || !this.recipe.notes.id) {
                    this.notes = res.body;
                } else {
                    this.notesService.find(this.recipe.notes.id).subscribe(
                        (subRes: HttpResponse<INotes>) => {
                            this.notes = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.categoryService.query().subscribe(
            (res: HttpResponse<ICategory[]>) => {
                this.categories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.recipe.id !== undefined) {
            this.subscribeToSaveResponse(this.recipeService.update(this.recipe));
        } else {
            this.subscribeToSaveResponse(this.recipeService.create(this.recipe));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRecipe>>) {
        result.subscribe((res: HttpResponse<IRecipe>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackNotesById(index: number, item: INotes) {
        return item.id;
    }

    trackCategoryById(index: number, item: ICategory) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get recipe() {
        return this._recipe;
    }

    set recipe(recipe: IRecipe) {
        this._recipe = recipe;
    }
}
