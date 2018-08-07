import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { RecipeComponentsPage, RecipeUpdatePage } from './recipe.page-object';
import * as path from 'path';

describe('Recipe e2e test', () => {
    let navBarPage: NavBarPage;
    let recipeUpdatePage: RecipeUpdatePage;
    let recipeComponentsPage: RecipeComponentsPage;
    const fileToUpload = '../../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Recipes', () => {
        navBarPage.goToEntity('recipe');
        recipeComponentsPage = new RecipeComponentsPage();
        expect(recipeComponentsPage.getTitle()).toMatch(/Recipes/);
    });

    it('should load create Recipe page', () => {
        recipeComponentsPage.clickOnCreateButton();
        recipeUpdatePage = new RecipeUpdatePage();
        expect(recipeUpdatePage.getPageTitle()).toMatch(/Create or edit a Recipe/);
        recipeUpdatePage.cancel();
    });

    it('should create and save Recipes', () => {
        recipeComponentsPage.clickOnCreateButton();
        recipeUpdatePage.setDescriptionInput('description');
        expect(recipeUpdatePage.getDescriptionInput()).toMatch('description');
        recipeUpdatePage.setPrepTimeInput('5');
        expect(recipeUpdatePage.getPrepTimeInput()).toMatch('5');
        recipeUpdatePage.setCookTimeInput('5');
        expect(recipeUpdatePage.getCookTimeInput()).toMatch('5');
        recipeUpdatePage.setServingsInput('5');
        expect(recipeUpdatePage.getServingsInput()).toMatch('5');
        recipeUpdatePage.setSourceInput('source');
        expect(recipeUpdatePage.getSourceInput()).toMatch('source');
        recipeUpdatePage.setUrlInput('url');
        expect(recipeUpdatePage.getUrlInput()).toMatch('url');
        recipeUpdatePage.setDirectionsInput('directions');
        expect(recipeUpdatePage.getDirectionsInput()).toMatch('directions');
        recipeUpdatePage.setImageInput(absolutePath);
        recipeUpdatePage.difficultySelectLastOption();
        recipeUpdatePage.notesSelectLastOption();
        // recipeUpdatePage.categorySelectLastOption();
        recipeUpdatePage.save();
        expect(recipeUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
