import { element, by, promise, ElementFinder } from 'protractor';

export class UnitOfMeasureComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-unit-of-measure div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getText();
    }
}

export class UnitOfMeasureUpdatePage {
    pageTitle = element(by.id('jhi-unit-of-measure-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    descriptionInput = element(by.id('field_description'));

    getPageTitle() {
        return this.pageTitle.getText();
    }

    setDescriptionInput(description): promise.Promise<void> {
        return this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
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
