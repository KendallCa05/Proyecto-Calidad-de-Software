package proyecto.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Detalle de un producto (inventory-item.html?id=N). */
public class ProductDetailPage extends BasePage {
    private final By name = By.cssSelector("[data-test='inventory-item-name']");
    private final By price = By.cssSelector("[data-test='inventory-item-price']");
    private final By backButton = By.cssSelector("[data-test='back-to-products']");

    public ProductDetailPage(WebDriver driver) {
        super(driver);
        waitForUrl("/inventory-item.html");
        text(name); // espera a que el detalle esté visible
    }

    public String name() { return text(name); }
    public double price() { return Double.parseDouble(text(price).replace("$", "")); }
    public String url() { return driver.getCurrentUrl(); }
    public InventoryPage backToProducts() { click(backButton); return new InventoryPage(driver); }
}
