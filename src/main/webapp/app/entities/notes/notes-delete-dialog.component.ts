import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INotes } from 'app/shared/model/notes.model';
import { NotesService } from './notes.service';

@Component({
    selector: 'jhi-notes-delete-dialog',
    templateUrl: './notes-delete-dialog.component.html'
})
export class NotesDeleteDialogComponent {
    notes: INotes;

    constructor(private notesService: NotesService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.notesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'notesListModification',
                content: 'Deleted an notes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-notes-delete-popup',
    template: ''
})
export class NotesDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ notes }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(NotesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.notes = notes;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
