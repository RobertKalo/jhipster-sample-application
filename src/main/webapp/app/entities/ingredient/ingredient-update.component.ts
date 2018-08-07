import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IIngredient } from 'app/shared/model/ingredient.model';
import { IngredientService } from './ingredient.service';
import { IUnitOfMeasure } from 'app/shared/model/unit-of-measure.model';
import { UnitOfMeasureService } from 'app/entities/unit-of-measure';
import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe';

@Component({
    selector: 'jhi-ingredient-update',
    templateUrl: './ingredient-update.component.html'
})
export class IngredientUpdateComponent implements OnInit {
    private _ingredient: IIngredient;
    isSaving: boolean;

    uoms: IUnitOfMeasure[];

    recipes: IRecipe[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private ingredientService: IngredientService,
        private unitOfMeasureService: UnitOfMeasureService,
        private recipeService: RecipeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ingredient }) => {
            this.ingredient = ingredient;
        });
        this.unitOfMeasureService.query({ filter: 'ingredient-is-null' }).subscribe(
            (res: HttpResponse<IUnitOfMeasure[]>) => {
                if (!this.ingredient.uom || !this.ingredient.uom.id) {
                    this.uoms = res.body;
                } else {
                    this.unitOfMeasureService.find(this.ingredient.uom.id).subscribe(
                        (subRes: HttpResponse<IUnitOfMeasure>) => {
                            this.uoms = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        if (this.ingredient.id !== undefined) {
            this.subscribeToSaveResponse(this.ingredientService.update(this.ingredient));
        } else {
            this.subscribeToSaveResponse(this.ingredientService.create(this.ingredient));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IIngredient>>) {
        result.subscribe((res: HttpResponse<IIngredient>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUnitOfMeasureById(index: number, item: IUnitOfMeasure) {
        return item.id;
    }

    trackRecipeById(index: number, item: IRecipe) {
        return item.id;
    }
    get ingredient() {
        return this._ingredient;
    }

    set ingredient(ingredient: IIngredient) {
        this._ingredient = ingredient;
    }
}
