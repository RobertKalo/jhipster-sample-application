import { element, by, promise, ElementFinder } from 'protractor';

export class NotesComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-notes div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getText();
    }
}

export class NotesUpdatePage {
    pageTitle = element(by.id('jhi-notes-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    recipeNotesInput = element(by.id('field_recipeNotes'));

    getPageTitle() {
        return this.pageTitle.getText();
    }

    setRecipeNotesInput(recipeNotes): promise.Promise<void> {
        return this.recipeNotesInput.sendKeys(recipeNotes);
    }

    getRecipeNotesInput() {
        return this.recipeNotesInput.getAttribute('value');
    }

    save(): promise.Promise<void> {
        return this.saveButton.click();
    }

    cancel(): promise.Promise<void> {
        return this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}
