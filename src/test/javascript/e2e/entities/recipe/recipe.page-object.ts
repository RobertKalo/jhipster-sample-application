import { element, by, promise, ElementFinder } from 'protractor';

export class RecipeComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-recipe div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getText();
    }
}

export class RecipeUpdatePage {
    pageTitle = element(by.id('jhi-recipe-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    descriptionInput = element(by.id('field_description'));
    prepTimeInput = element(by.id('field_prepTime'));
    cookTimeInput = element(by.id('field_cookTime'));
    servingsInput = element(by.id('field_servings'));
    sourceInput = element(by.id('field_source'));
    urlInput = element(by.id('field_url'));
    directionsInput = element(by.id('field_directions'));
    imageInput = element(by.id('file_image'));
    difficultySelect = element(by.id('field_difficulty'));
    notesSelect = element(by.id('field_notes'));
    categorySelect = element(by.id('field_category'));

    getPageTitle() {
        return this.pageTitle.getText();
    }

    setDescriptionInput(description): promise.Promise<void> {
        return this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    setPrepTimeInput(prepTime): promise.Promise<void> {
        return this.prepTimeInput.sendKeys(prepTime);
    }

    getPrepTimeInput() {
        return this.prepTimeInput.getAttribute('value');
    }

    setCookTimeInput(cookTime): promise.Promise<void> {
        return this.cookTimeInput.sendKeys(cookTime);
    }

    getCookTimeInput() {
        return this.cookTimeInput.getAttribute('value');
    }

    setServingsInput(servings): promise.Promise<void> {
        return this.servingsInput.sendKeys(servings);
    }

    getServingsInput() {
        return this.servingsInput.getAttribute('value');
    }

    setSourceInput(source): promise.Promise<void> {
        return this.sourceInput.sendKeys(source);
    }

    getSourceInput() {
        return this.sourceInput.getAttribute('value');
    }

    setUrlInput(url): promise.Promise<void> {
        return this.urlInput.sendKeys(url);
    }

    getUrlInput() {
        return this.urlInput.getAttribute('value');
    }

    setDirectionsInput(directions): promise.Promise<void> {
        return this.directionsInput.sendKeys(directions);
    }

    getDirectionsInput() {
        return this.directionsInput.getAttribute('value');
    }

    setImageInput(image): promise.Promise<void> {
        return this.imageInput.sendKeys(image);
    }

    getImageInput() {
        return this.imageInput.getAttribute('value');
    }

    setDifficultySelect(difficulty): promise.Promise<void> {
        return this.difficultySelect.sendKeys(difficulty);
    }

    getDifficultySelect() {
        return this.difficultySelect.element(by.css('option:checked')).getText();
    }

    difficultySelectLastOption(): promise.Promise<void> {
        return this.difficultySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }
    notesSelectLastOption(): promise.Promise<void> {
        return this.notesSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    notesSelectOption(option): promise.Promise<void> {
        return this.notesSelect.sendKeys(option);
    }

    getNotesSelect(): ElementFinder {
        return this.notesSelect;
    }

    getNotesSelectedOption() {
        return this.notesSelect.element(by.css('option:checked')).getText();
    }

    categorySelectLastOption(): promise.Promise<void> {
        return this.categorySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    categorySelectOption(option): promise.Promise<void> {
        return this.categorySelect.sendKeys(option);
    }

    getCategorySelect(): ElementFinder {
        return this.categorySelect;
    }

    getCategorySelectedOption() {
        return this.categorySelect.element(by.css('option:checked')).getText();
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
