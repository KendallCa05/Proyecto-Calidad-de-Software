package proyecto.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
}
