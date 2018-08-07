import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INotes } from 'app/shared/model/notes.model';

@Component({
    selector: 'jhi-notes-detail',
    templateUrl: './notes-detail.component.html'
})
export class NotesDetailComponent implements OnInit {
    notes: INotes;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ notes }) => {
            this.notes = notes;
        });
    }

    previousState() {
        window.history.back();
    }
}
