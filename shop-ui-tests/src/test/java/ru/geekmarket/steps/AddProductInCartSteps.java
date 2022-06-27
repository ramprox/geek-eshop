package ru.geekmarket.steps;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class AddProductInCartSteps {

    private WebDriver webDriver = null;

    private WebElement addedTableRow;

    @Given("I open browser")
    public void iOpenWebBrowser() {
        webDriver = DriverInitializer.getDriver();
    }

    @When("navigate to products.html in shop-backend-app")
    public void navigateToProductsInShopBackendApp() throws InterruptedException {
        Thread.sleep(5000);
        webDriver.get(DriverInitializer.getProperty("backend.products.url"));
    }

    @When("click to button \"Add to cart\" for first product on page")
    public void clickToAddToCart() throws InterruptedException {
        Thread.sleep(5000);
        WebElement productGalleryCard = webDriver.findElement(By.tagName("app-product-gallery-card"));
        List<WebElement> productCards = productGalleryCard.findElements(By.className("border-primary"));
        WebElement productCard = productCards.get(0);
        WebElement btnAddToCart = productCard.findElements(By.className("btn")).get(1);
        btnAddToCart.click();
    }

    @When("after i navigate to cart.html")
    public void navigateToCartPage() throws InterruptedException {
        Thread.sleep(3000);
        WebElement nav = webDriver.findElement(By.tagName("nav"));
        WebElement navBarToggler = nav.findElement(By.className("navbar-toggler"));
        if(navBarToggler.isDisplayed()) {
            navBarToggler.click();
        }
        List<WebElement> lis = nav.findElements(By.tagName("li"));
        assertThat(lis.size() == 3);
        WebElement cartLink = lis.stream()
                .filter(li -> li.getText().equals("Cart"))
                .findFirst().get();
        cartLink.click();
    }

    @Then("added products count in cart must be 1")
    public void checkAddedProduct() throws InterruptedException {
        Thread.sleep(3000);
        WebElement tableBody = webDriver.findElement(By.tagName("tbody"));
        List<WebElement> tableRows = tableBody.findElements(By.tagName("tr"));
        assertThat(tableRows.size() == 1);
        addedTableRow = tableRows.get(0);
    }

    @When("after click to \"Delete line item\" button")
    public void clickToDeleteLineItemBtn() throws InterruptedException {
        Thread.sleep(3000);
        WebElement deleteBtn = addedTableRow.findElement(By.className("btn-danger"));
        deleteBtn.click();
    }

    @Then("added product must be removed from page")
    public void addedProductMustBeRemoved() throws InterruptedException {
        Thread.sleep(3000);
        WebElement tableBody = webDriver.findElement(By.tagName("tbody"));
        List<WebElement> tableRows = tableBody.findElements(By.tagName("tr"));
        assertThat(tableRows.size() == 1);
    }

    @After
    public void quitBrowser() {
        if(webDriver != null) {
            webDriver.quit();
        }
    }
}
