package ru.geekmarket.steps;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AddCategorySteps {

    private WebDriver webDriver = null;

    private WebElement addedRow;

    private String categoryName;

    @Given("I login in shop-admin-app providing username as {string} and password as {string}")
    public void iLoginInShopAdminApp(String username, String password) throws Throwable {
        webDriver = DriverInitializer.getDriver();
        webDriver.get(DriverInitializer.getProperty("login.url"));
        WebElement elUsername = webDriver.findElement(By.id("username"));
        elUsername.sendKeys(username);
        WebElement elPassword = webDriver.findElement(By.id("password"));
        elPassword.sendKeys(password);
        WebElement btnLogin = webDriver.findElement(By.id("btn-login"));
        btnLogin.click();
    }

    @When("^I navigate to category.html page$")
    public void iNavigateToCategoryHtmlPage() throws InterruptedException {
        Thread.sleep(3000);
        WebElement navBarToggler = webDriver.findElement(By.className("navbar-toggler"));
        if(navBarToggler.isDisplayed()) {
            navBarToggler.click();
        }
        WebElement elCategory = webDriver.findElement(By.id("category"));
        elCategory.click();
    }

    @When("^click add category button$")
    public void clickAddCategoryButton() throws InterruptedException {
        Thread.sleep(3000);
        WebElement addCategoryBtn = webDriver.findElement(By.id("addCategoryBtn"));
        addCategoryBtn.click();
    }

    @When("I provide category name as {string} and click submit button")
    public void provideCategoryNameAndClickSubmit(String categoryName) throws InterruptedException {
        Thread.sleep(3000);
        WebElement inputField = webDriver.findElement(By.id("name"));
        inputField.sendKeys(categoryName);
        WebElement submitBtn = webDriver.findElement(By.id("submit"));
        submitBtn.click();
        this.categoryName = categoryName;
    }

    @Then("url must be equals category.html page url")
    public void urlMustBeCategoryHtml() throws InterruptedException {
        Thread.sleep(3000);
        assertThat(webDriver.getCurrentUrl().equals(DriverInitializer.getProperty("categories.url")));
    }

    @Then("check added category")
    public void checkAddedCategory() throws InterruptedException {
        Thread.sleep(3000);
        boolean findAddedCategory = false;
        List<WebElement> rows = webDriver.findElement(By.tagName("tbody"))
                .findElements(By.tagName("tr"));
        for(WebElement row : rows) {
            WebElement td = row.findElement(By.tagName("td"));
            String text = td.getText();
            if(!text.isEmpty() && text.equals(categoryName)) {
                findAddedCategory = true;
                addedRow = row;
                JavascriptExecutor executor = (JavascriptExecutor) webDriver;
                executor.executeScript("arguments[0].scrollIntoView();", row);
            }
        }
        assertThat(findAddedCategory);
    }

    @Then("remove added category")
    public void removeAddedCategory() throws InterruptedException {
        Thread.sleep(3000);
        WebElement deleteBtn = addedRow.findElement(By.className("btn-danger"));
        deleteBtn.submit();
    }

    @Then("check removed category")
    public void checkRemovedCategory() throws InterruptedException {
        Thread.sleep(3000);
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        executor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(3000);
        boolean findAddedCategory = false;
        List<WebElement> rows = webDriver.findElement(By.tagName("tbody"))
                .findElements(By.tagName("tr"));
        for(WebElement row : rows) {
            WebElement td = row.findElement(By.tagName("td"));
            String text = td.getText();
            if(!text.isEmpty() && text.equals(categoryName)) {
                findAddedCategory = true;
                addedRow = row;
            }
        }
        assertThat(!findAddedCategory);
    }

    @After
    public void quitBrowser() {
        if(webDriver != null) {
            webDriver.quit();
        }
    }
}
