package proyecto.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Operaciones comunes de espera e interacción para las páginas del sitio. */
public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void click(By locator) { wait.until(ExpectedConditions.elementToBeClickable(locator)).click(); }
    protected String text(By locator) {
        return wait.until(d -> {
            try {
                var element = d.findElement(locator);
                return element.isDisplayed() ? element.getText() : null;
            } catch (StaleElementReferenceException | NoSuchElementException e) {
                return null;
            }
        });
    }
    protected void type(By locator, String value) {
        var field = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        field.clear();
        field.sendKeys(value);
    }
    protected boolean displayed(By locator) {
        try { return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed(); }
        catch (org.openqa.selenium.TimeoutException e) { return false; }
    }
    protected void waitForText(By locator, String expected) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, expected));
    }
    protected void waitForUrl(String fragment) {
        wait.until(ExpectedConditions.urlContains(fragment));
    }
}
