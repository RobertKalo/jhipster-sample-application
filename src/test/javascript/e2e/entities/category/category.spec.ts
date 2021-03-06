import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { CategoryComponentsPage, CategoryUpdatePage } from './category.page-object';

describe('Category e2e test', () => {
    let navBarPage: NavBarPage;
    let categoryUpdatePage: CategoryUpdatePage;
    let categoryComponentsPage: CategoryComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Categories', () => {
        navBarPage.goToEntity('category');
        categoryComponentsPage = new CategoryComponentsPage();
        expect(categoryComponentsPage.getTitle()).toMatch(/Categories/);
    });

    it('should load create Category page', () => {
        categoryComponentsPage.clickOnCreateButton();
        categoryUpdatePage = new CategoryUpdatePage();
        expect(categoryUpdatePage.getPageTitle()).toMatch(/Create or edit a Category/);
        categoryUpdatePage.cancel();
    });

    it('should create and save Categories', () => {
        categoryComponentsPage.clickOnCreateButton();
        categoryUpdatePage.setDescriptionInput('description');
        expect(categoryUpdatePage.getDescriptionInput()).toMatch('description');
        categoryUpdatePage.save();
        expect(categoryUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
