package proyecto.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import proyecto.base.BaseTest;
import proyecto.base.Config;
import proyecto.base.Datos;
import proyecto.pages.InventoryPage;
import proyecto.pages.LoginPage;

/** Login (RF-LOG-01 a RF-LOG-04). Casos CP-16 a CP-29. */
class LoginTest extends BaseTest {

    @ParameterizedTest(name = "{0} {1}")
    @CsvFileSource(resources = "/login_cases.csv", numLinesToSkip = 1)
    @DisplayName("Login parametrizado")
    void loginSegunCasos(String caso, String descripcion, String usuario, String clave, String resultado, String mensaje) {
        String user = usuario == null ? "" : usuario;
        String pass = clave == null ? "" : clave.replace("{PASSWORD}", Config.password());
        LoginPage login = abrirLogin();

        if ("OK".equals(resultado)) {
            InventoryPage inventario = login.login(user, pass);
            assertEquals("Products", inventario.title(), caso + ": debe mostrar el inventario");
        } else {
            login.loginExpectingError(user, pass);
            assertEquals(mensaje, login.errorMessage(), caso + ": mensaje de error");
            assertTrue(login.isLoaded(), caso + ": debe permanecer en el login");
        }
    }

    @Test
    @DisplayName("CP-26 Acceso directo a inventory.html sin sesión es rechazado")
    void cp26AccesoSinSesion() {
        LoginPage login = new LoginPage(driver).open(Config.baseUrl() + "inventory.html");
        assertEquals("Epic sadface: You can only access '/inventory.html' when you are logged in.", login.errorMessage());
        assertTrue(login.isLoaded());
    }

    @Test
    @DisplayName("CP-28 Login con problem_user llega al inventario")
    void cp28LoginProblemUser() {
        InventoryPage inventario = abrirLogin().login(Datos.USUARIO_PROBLEMA, Config.password());
        assertEquals("Products", inventario.title());
    }

    @Test
    @DisplayName("CP-29 Login con performance_glitch_user muestra el inventario en menos de 10 s")
    void cp29LoginUsuarioLento() {
        long inicio = System.nanoTime();
        InventoryPage inventario = abrirLogin().login(Datos.USUARIO_LENTO, Config.password()).waitForProducts();
        long ms = (System.nanoTime() - inicio) / 1_000_000;
        System.out.println("CP-29 performance_glitch_user: inventario visible en " + ms + " ms");
        assertEquals(6, inventario.productCount());
        assertTrue(ms < 10_000, "El inventario tardó " + ms + " ms");
    }
}
