import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IUnitOfMeasure } from 'app/shared/model/unit-of-measure.model';
import { UnitOfMeasureService } from './unit-of-measure.service';

@Component({
    selector: 'jhi-unit-of-measure-update',
    templateUrl: './unit-of-measure-update.component.html'
})
export class UnitOfMeasureUpdateComponent implements OnInit {
    private _unitOfMeasure: IUnitOfMeasure;
    isSaving: boolean;

    constructor(private unitOfMeasureService: UnitOfMeasureService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ unitOfMeasure }) => {
            this.unitOfMeasure = unitOfMeasure;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.unitOfMeasure.id !== undefined) {
            this.subscribeToSaveResponse(this.unitOfMeasureService.update(this.unitOfMeasure));
        } else {
            this.subscribeToSaveResponse(this.unitOfMeasureService.create(this.unitOfMeasure));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUnitOfMeasure>>) {
        result.subscribe((res: HttpResponse<IUnitOfMeasure>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get unitOfMeasure() {
        return this._unitOfMeasure;
    }

    set unitOfMeasure(unitOfMeasure: IUnitOfMeasure) {
        this._unitOfMeasure = unitOfMeasure;
    }
}
