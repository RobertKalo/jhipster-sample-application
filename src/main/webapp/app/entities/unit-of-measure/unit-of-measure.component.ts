import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUnitOfMeasure } from 'app/shared/model/unit-of-measure.model';
import { Principal } from 'app/core';
import { UnitOfMeasureService } from './unit-of-measure.service';

@Component({
    selector: 'jhi-unit-of-measure',
    templateUrl: './unit-of-measure.component.html'
})
export class UnitOfMeasureComponent implements OnInit, OnDestroy {
    unitOfMeasures: IUnitOfMeasure[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private unitOfMeasureService: UnitOfMeasureService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.unitOfMeasureService.query().subscribe(
            (res: HttpResponse<IUnitOfMeasure[]>) => {
                this.unitOfMeasures = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInUnitOfMeasures();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IUnitOfMeasure) {
        return item.id;
    }

    registerChangeInUnitOfMeasures() {
        this.eventSubscriber = this.eventManager.subscribe('unitOfMeasureListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
