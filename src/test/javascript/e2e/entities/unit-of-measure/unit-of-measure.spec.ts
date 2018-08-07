import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { UnitOfMeasureComponentsPage, UnitOfMeasureUpdatePage } from './unit-of-measure.page-object';

describe('UnitOfMeasure e2e test', () => {
    let navBarPage: NavBarPage;
    let unitOfMeasureUpdatePage: UnitOfMeasureUpdatePage;
    let unitOfMeasureComponentsPage: UnitOfMeasureComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load UnitOfMeasures', () => {
        navBarPage.goToEntity('unit-of-measure');
        unitOfMeasureComponentsPage = new UnitOfMeasureComponentsPage();
        expect(unitOfMeasureComponentsPage.getTitle()).toMatch(/Unit Of Measures/);
    });

    it('should load create UnitOfMeasure page', () => {
        unitOfMeasureComponentsPage.clickOnCreateButton();
        unitOfMeasureUpdatePage = new UnitOfMeasureUpdatePage();
        expect(unitOfMeasureUpdatePage.getPageTitle()).toMatch(/Create or edit a Unit Of Measure/);
        unitOfMeasureUpdatePage.cancel();
    });

    it('should create and save UnitOfMeasures', () => {
        unitOfMeasureComponentsPage.clickOnCreateButton();
        unitOfMeasureUpdatePage.setDescriptionInput('description');
        expect(unitOfMeasureUpdatePage.getDescriptionInput()).toMatch('description');
        unitOfMeasureUpdatePage.save();
        expect(unitOfMeasureUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
