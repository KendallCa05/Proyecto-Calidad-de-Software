package proyecto.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {
    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By postalCode = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By finishButton = By.id("finish");
    private final By cancelButton = By.id("cancel");
    private final By error = By.cssSelector("[data-test='error']");
    private final By subtotal = By.className("summary_subtotal_label");
    private final By tax = By.className("summary_tax_label");
    private final By total = By.className("summary_total_label");
    private final By completeHeader = By.className("complete-header");
    public CheckoutPage(WebDriver driver) {
        super(driver);
        waitForUrl("/checkout-step-one.html");
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(firstName));
    }
    public void enterCustomer(String first, String last, String postal) {
        type(firstName, first); type(lastName, last); type(postalCode, postal);
    }
    public void continueToOverview() { click(continueButton); }
    public void finishPurchase() { click(finishButton); }
    public void cancel() { click(cancelButton); }
    public String errorMessage() { return text(error); }
    public String subtotal() { return text(subtotal); }
    public String tax() { return text(tax); }
    public String total() { return text(total); }
    public String confirmation() { return text(completeHeader); }
    public int overviewItemCount() { return driver.findElements(By.className("cart_item")).size(); }
}
