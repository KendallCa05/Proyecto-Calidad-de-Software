package proyecto.tests;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import proyecto.pages.*;

/** Pruebas automatizadas de carrito y compra. Por defecto usan Chrome. */
class CheckoutTest {
    private WebDriver driver;
    private LoginPage login;
    private InventoryPage inventory;
    private final String url = System.getProperty("baseUrl", "https://www.saucedemo.com/");
    private final String browser = System.getProperty("browser", "chrome").toLowerCase();
    private final String user = System.getProperty("testUser", System.getenv("SWAGLABS_TEST_USER"));
    private final String pass = System.getProperty("testPassword", System.getenv("SWAGLABS_TEST_PASSWORD"));

    @BeforeEach void setUp() {
        if (user == null || pass == null) {
            throw new IllegalStateException("Configura las credenciales demo con SWAGLABS_TEST_USER y SWAGLABS_TEST_PASSWORD.");
        }
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        switch (browser) {
            case "firefox" -> { FirefoxOptions o = new FirefoxOptions(); if (headless) o.addArguments("-headless"); driver = new FirefoxDriver(o); }
            case "edge" -> { EdgeOptions o = new EdgeOptions(); if (headless) o.addArguments("--headless=new"); driver = new EdgeDriver(o); }
            default -> { ChromeOptions o = new ChromeOptions(); if (headless) o.addArguments("--headless=new"); driver = new ChromeDriver(o); }
        }
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().window().maximize();
        login = new LoginPage(driver).open(url);
        inventory = login.login(user, pass);
    }
    @AfterEach void tearDown() { if (driver != null) driver.quit(); }

    @Test void agregarProductoActualizaContador() {
        inventory.addProduct("sauce-labs-backpack");
        assertEquals("1", inventory.cartCount());
    }
    @Test void agregarDosProductosActualizaContador() {
        inventory.addProduct("sauce-labs-backpack"); inventory.addProduct("sauce-labs-bike-light");
        assertEquals("2", inventory.cartCount());
    }
    @Test void quitarProductoDesdeInventarioActualizaContador() {
        inventory.addProduct("sauce-labs-backpack"); inventory.removeProduct("sauce-labs-backpack");
        assertEquals("0", inventory.cartCount());
    }
    @Test void carritoMuestraProductosAgregados() {
        inventory.addProduct("sauce-labs-backpack"); inventory.addProduct("sauce-labs-bike-light");
        CartPage cart = inventory.openCart();
        assertEquals(2, cart.itemCount());
        assertTrue(cart.hasProduct("sauce-labs-backpack"));
    }
    @Test void quitarProductoDesdeCarrito() {
        inventory.addProduct("sauce-labs-backpack"); inventory.openCart().removeProduct("sauce-labs-backpack");
        CartPage cart = new CartPage(driver);
        assertEquals(0, cart.itemCount()); assertEquals("0", cart.cartCount());
    }
    @Test void continuarComprandoRegresaAlInventario() {
        CartPage cart = inventory.addAndOpenCart("sauce-labs-backpack");
        assertEquals("Products", cart.continueShopping().title());
    }
    @Test void checkoutExigeNombre() {
        CheckoutPage checkout = inventory.addAndOpenCart("sauce-labs-backpack").checkout();
        checkout.continueToOverview();
        assertTrue(checkout.errorMessage().toLowerCase().contains("first name"));
    }
    @Test void checkoutExigeApellido() {
        CheckoutPage checkout = inventory.addAndOpenCart("sauce-labs-backpack").checkout();
        checkout.enterCustomer("Test", "", "00000"); checkout.continueToOverview();
        assertTrue(checkout.errorMessage().toLowerCase().contains("last name"));
    }
    @Test void checkoutExigeCodigoPostal() {
        CheckoutPage checkout = inventory.addAndOpenCart("sauce-labs-backpack").checkout();
        checkout.enterCustomer("Test", "Buyer", ""); checkout.continueToOverview();
        assertTrue(checkout.errorMessage().toLowerCase().contains("postal code"));
    }
    @Test void compradorValidoVeResumen() {
        CheckoutPage checkout = startValidCheckout("sauce-labs-backpack");
        assertEquals(1, checkout.overviewItemCount());
    }
    @Test void resumenMuestraSubtotalImpuestoYTotal() {
        CheckoutPage checkout = startValidCheckout("sauce-labs-backpack");
        assertTrue(checkout.subtotal().contains("29.99"));
        assertTrue(checkout.tax().contains("Tax")); assertTrue(checkout.total().contains("Total"));
    }
    @Test void totalEsSubtotalMasImpuesto() {
        CheckoutPage checkout = startValidCheckout("sauce-labs-backpack");
        double sub = money(checkout.subtotal()), tax = money(checkout.tax()), total = money(checkout.total());
        assertEquals(sub + tax, total, 0.011);
    }
    @Test void confirmarCompraMuestraMensaje() {
        CheckoutPage checkout = startValidCheckout("sauce-labs-backpack"); checkout.finishPurchase();
        assertTrue(checkout.confirmation().toLowerCase().contains("thank you"));
    }
    @Test void compraIncluyeLosDosProductos() {
        CheckoutPage checkout = startValidCheckout("sauce-labs-backpack", "sauce-labs-bike-light");
        assertEquals(2, checkout.overviewItemCount());
    }
    @Test void cancelarDesdeDatosRegresaAlCarrito() {
        CheckoutPage checkout = inventory.addAndOpenCart("sauce-labs-backpack").checkout(); checkout.cancel();
        assertEquals("Your Cart", new CartPage(driver).title());
    }

    private CheckoutPage startValidCheckout(String... products) {
        for (String product : products) inventory.addProduct(product);
        CheckoutPage checkout = inventory.openCart().checkout();
        checkout.enterCustomer("Test", "Buyer", "00000"); checkout.continueToOverview();
        return checkout;
    }
    private double money(String label) { return Double.parseDouble(label.replaceAll("[^0-9.]", "")); }
}
