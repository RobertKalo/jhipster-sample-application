import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { INotes } from 'app/shared/model/notes.model';
import { Principal } from 'app/core';
import { NotesService } from './notes.service';

@Component({
    selector: 'jhi-notes',
    templateUrl: './notes.component.html'
})
export class NotesComponent implements OnInit, OnDestroy {
    notes: INotes[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private notesService: NotesService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.notesService.query().subscribe(
            (res: HttpResponse<INotes[]>) => {
                this.notes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInNotes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: INotes) {
        return item.id;
    }

    registerChangeInNotes() {
        this.eventSubscriber = this.eventManager.subscribe('notesListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
