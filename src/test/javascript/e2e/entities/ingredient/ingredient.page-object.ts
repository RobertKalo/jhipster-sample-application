import { element, by, promise, ElementFinder } from 'protractor';

export class IngredientComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-ingredient div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getText();
    }
}

export class IngredientUpdatePage {
    pageTitle = element(by.id('jhi-ingredient-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    descriptionInput = element(by.id('field_description'));
    amountInput = element(by.id('field_amount'));
    uomSelect = element(by.id('field_uom'));
    recipeSelect = element(by.id('field_recipe'));

    getPageTitle() {
        return this.pageTitle.getText();
    }

    setDescriptionInput(description): promise.Promise<void> {
        return this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    setAmountInput(amount): promise.Promise<void> {
        return this.amountInput.sendKeys(amount);
    }

    getAmountInput() {
        return this.amountInput.getAttribute('value');
    }

    uomSelectLastOption(): promise.Promise<void> {
        return this.uomSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    uomSelectOption(option): promise.Promise<void> {
        return this.uomSelect.sendKeys(option);
    }

    getUomSelect(): ElementFinder {
        return this.uomSelect;
    }

    getUomSelectedOption() {
        return this.uomSelect.element(by.css('option:checked')).getText();
    }

    recipeSelectLastOption(): promise.Promise<void> {
        return this.recipeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    recipeSelectOption(option): promise.Promise<void> {
        return this.recipeSelect.sendKeys(option);
    }

    getRecipeSelect(): ElementFinder {
        return this.recipeSelect;
    }

    getRecipeSelectedOption() {
        return this.recipeSelect.element(by.css('option:checked')).getText();
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
