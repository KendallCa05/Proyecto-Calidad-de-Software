package proyecto.pages;

import org.openqa.selenium.By;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    // ---- Lectura del listado (login e inventario) ----
    private final By productItems = By.cssSelector("[data-test='inventory-item']");
    private final By productNames = By.cssSelector("[data-test='inventory-item-name']");
    private final By productPrices = By.cssSelector("[data-test='inventory-item-price']");
    private final By productImages = By.cssSelector("[data-test='inventory-item'] img");
    private final By sortSelect = By.cssSelector("[data-test='product-sort-container']");

    /** Espera a que el listado tenga al menos un producto (performance_glitch_user tarda varios segundos). */
    public InventoryPage waitForProducts() {
        wait.until(d -> !d.findElements(productItems).isEmpty());
        return this;
    }
    public int productCount() { return driver.findElements(productItems).size(); }
    public List<String> productNames() {
        List<String> out = new ArrayList<>();
        for (WebElement e : driver.findElements(productNames)) out.add(e.getText());
        return out;
    }
    public List<Double> productPrices() {
        List<Double> out = new ArrayList<>();
        for (WebElement e : driver.findElements(productPrices)) out.add(Double.parseDouble(e.getText().replace("$", "")));
        return out;
    }
    /** Nombre de archivo de la imagen de cada producto, en el orden mostrado. */
    public List<String> productImageFiles() {
        List<String> out = new ArrayList<>();
        for (WebElement e : driver.findElements(productImages)) {
            String src = e.getAttribute("src");
            out.add(src.substring(src.lastIndexOf('/') + 1));
        }
        return out;
    }

    // ---- Ordenamiento: az, za, lohi, hilo ----
    public void sortBy(String value) { new Select(wait.until(ExpectedConditions.elementToBeClickable(sortSelect))).selectByValue(value); }

    /**
     * Ordena y devuelve el texto de la alerta que muestre la página (error_user), o null si no hay alerta.
     * La alerta se acepta para que la prueba pueda seguir leyendo el listado.
     */
    public String sortByAndReadAlert(String value) {
        try {
            sortBy(value);
        } catch (UnhandledAlertException e) {
            return e.getAlertText();
        }
        try {
            Alert alert = new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.alertIsPresent());
            String text = alert.getText();
            alert.accept();
            return text;
        } catch (TimeoutException e) {
            return null;
        }
    }

    /** Pulsa Add to cart y devuelve true si el botón pasó a Remove en 2 s (no lanza excepción si no cambia). */
    public boolean tryAddProduct(String item) {
        click(addButton(item));
        if (removeAppears(item)) return true;
        click(addButton(item));
        return removeAppears(item);
    }

    private boolean removeAppears(String item) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(2))
                    .until(ExpectedConditions.visibilityOfElementLocated(removeButton(item))).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public ProductDetailPage openDetail(String productName) {
        click(By.linkText(productName));
        if (!detailOpened()) {
            click(By.linkText(productName));
        }
        return new ProductDetailPage(driver);
    }

    private boolean detailOpened() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.urlContains("/inventory-item.html"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
