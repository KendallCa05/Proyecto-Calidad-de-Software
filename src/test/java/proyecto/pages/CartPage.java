package proyecto.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {
    private final By checkout = By.id("checkout");
    private final By continueShopping = By.id("continue-shopping");
    private final By items = By.className("cart_item");
    private final By cartBadge = By.className("shopping_cart_badge");
    public CartPage(WebDriver driver) {
        super(driver);
        waitForUrl("/cart.html");
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(By.className("cart_list")));
    }
    public int itemCount() { return driver.findElements(items).size(); }
    public void removeProduct(String item) {
        By row = By.xpath("//div[contains(@class,'cart_item')][.//button[@id='remove-" + item + "']]");
        click(By.id("remove-" + item));
        wait.until(driver -> driver.findElements(row).isEmpty());
    }
    public boolean hasProduct(String item) { return !driver.findElements(By.id("remove-" + item)).isEmpty(); }
    public String cartCount() { return driver.findElements(cartBadge).isEmpty() ? "0" : text(cartBadge); }
    public CheckoutPage checkout() { click(checkout); return new CheckoutPage(driver); }
    public InventoryPage continueShopping() { click(continueShopping); return new InventoryPage(driver); }
    public String title() { return text(By.className("title")); }
}
