import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { IngredientComponentsPage, IngredientUpdatePage } from './ingredient.page-object';

describe('Ingredient e2e test', () => {
    let navBarPage: NavBarPage;
    let ingredientUpdatePage: IngredientUpdatePage;
    let ingredientComponentsPage: IngredientComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Ingredients', () => {
        navBarPage.goToEntity('ingredient');
        ingredientComponentsPage = new IngredientComponentsPage();
        expect(ingredientComponentsPage.getTitle()).toMatch(/Ingredients/);
    });

    it('should load create Ingredient page', () => {
        ingredientComponentsPage.clickOnCreateButton();
        ingredientUpdatePage = new IngredientUpdatePage();
        expect(ingredientUpdatePage.getPageTitle()).toMatch(/Create or edit a Ingredient/);
        ingredientUpdatePage.cancel();
    });

    it('should create and save Ingredients', () => {
        ingredientComponentsPage.clickOnCreateButton();
        ingredientUpdatePage.setDescriptionInput('description');
        expect(ingredientUpdatePage.getDescriptionInput()).toMatch('description');
        ingredientUpdatePage.setAmountInput('5');
        expect(ingredientUpdatePage.getAmountInput()).toMatch('5');
        ingredientUpdatePage.uomSelectLastOption();
        ingredientUpdatePage.recipeSelectLastOption();
        ingredientUpdatePage.save();
        expect(ingredientUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
