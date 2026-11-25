package proyecto.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage extends BasePage {
    private final By cartLink = By.className("shopping_cart_link");
    private final By cartBadge = By.className("shopping_cart_badge");
    public InventoryPage(WebDriver driver) {
        super(driver);
        waitForUrl("/inventory.html");
    }
    private By addButton(String item) { return By.id("add-to-cart-" + item); }
    private By removeButton(String item) { return By.id("remove-" + item); }
    public void addProduct(String item) {
        click(addButton(item));
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(removeButton(item)));
    }
    public void removeProduct(String item) {
        click(removeButton(item));
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(addButton(item)));
    }
    public String cartCount() { return driver.findElements(cartBadge).isEmpty() ? "0" : text(cartBadge); }
    public CartPage openCart() { click(cartLink); return new CartPage(driver); }
    public CartPage addAndOpenCart(String item) { addProduct(item); return openCart(); }
    public String title() { return text(By.className("title")); }
}
