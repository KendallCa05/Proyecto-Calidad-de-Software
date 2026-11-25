package proyecto.base;

import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import proyecto.pages.InventoryPage;
import proyecto.pages.LoginPage;

/** Abre un navegador nuevo antes de cada test y lo cierra después, de modo que cada test es independiente. */
@ExtendWith(ScreenshotOnFailure.class)
public abstract class BaseTest {
    private static final ThreadLocal<WebDriver> CURRENT = new ThreadLocal<>();
    protected WebDriver driver;

    static WebDriver currentDriver() { return CURRENT.get(); }

    @BeforeEach
    protected void abrirNavegador() {
        boolean headless = Config.headless();
        switch (Config.browser()) {
            case "firefox" -> { FirefoxOptions o = new FirefoxOptions(); if (headless) o.addArguments("-headless"); driver = new FirefoxDriver(o); }
            case "edge" -> { EdgeOptions o = new EdgeOptions(); if (headless) o.addArguments("--headless=new"); driver = new EdgeDriver(o); }
            default -> { ChromeOptions o = new ChromeOptions(); if (headless) o.addArguments("--headless=new"); driver = new ChromeDriver(o); }
        }
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().window().maximize();
        CURRENT.set(driver);
    }

    @AfterEach
    protected void cerrarNavegador() {
        if (driver != null) driver.quit();
        CURRENT.remove();
    }

    protected LoginPage abrirLogin() {
        return new LoginPage(driver).open(Config.baseUrl());
    }

    /** Inicia sesión con la contraseña de demostración y espera a que el listado tenga productos. */
    protected InventoryPage entrarComo(String usuario) {
        return abrirLogin().login(usuario, Config.password()).waitForProducts();
    }
}
