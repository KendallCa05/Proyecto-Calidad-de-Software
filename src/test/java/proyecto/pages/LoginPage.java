package proyecto.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    private final By username = By.id("user-name");
    private final By password = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By inventoryTitle = By.className("title");
    private final By error = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) { super(driver); }
    public LoginPage open(String url) { driver.get(url); return this; }
    public InventoryPage login(String user, String pass) {
        type(username, user); type(password, pass); click(loginButton);
        return new InventoryPage(driver);
    }
    public void loginExpectingError(String user, String pass) {
        type(username, user); type(password, pass); click(loginButton);
    }
    public String errorMessage() { return text(error); }
    public boolean isLoaded() { return displayed(loginButton); }
    /** Cierra el mensaje de error con el botón X. */
    public void closeError() { click(By.cssSelector("[data-test='error-button']")); }
    /** Indica si el mensaje de error desapareció (espera hasta 2 s). */
    public boolean errorGone() {
        try { return new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(2))
                .until(ExpectedConditions.invisibilityOfElementLocated(error)); }
        catch (org.openqa.selenium.TimeoutException e) { return false; }
    }
    public String currentUrl() { return driver.getCurrentUrl(); }
}
