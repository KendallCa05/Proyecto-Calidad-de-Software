package proyecto.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import proyecto.base.BaseTest;
import proyecto.base.Config;
import proyecto.base.Datos;
import proyecto.pages.InventoryPage;
import proyecto.pages.ProductDetailPage;

class UsuariosConDefectosTest extends BaseTest {

    @Test
    @DisplayName("CP-36 problem_user: cada producto muestra su propia imagen")
    void cp36ImagenesProblemUser() {
        InventoryPage inventario = entrarComo(Datos.USUARIO_PROBLEMA);
        assertEquals(6, new HashSet<>(inventario.productImageFiles()).size(),
                "Imágenes mostradas: " + inventario.productImageFiles());
    }

    @Test
    @DisplayName("CP-37 problem_user: ordenar por nombre de Z a A")
    void cp37OrdenProblemUser() {
        InventoryPage inventario = entrarComo(Datos.USUARIO_PROBLEMA);
        inventario.sortBy("za");
        List<String> esperado = new ArrayList<>(Datos.NOMBRES);
        esperado.sort(Collections.reverseOrder());
        assertEquals(esperado, inventario.productNames());
    }

    @Test
    @DisplayName("CP-38 problem_user: el detalle corresponde al producto elegido")
    void cp38DetalleProblemUser() {
        InventoryPage inventario = entrarComo(Datos.USUARIO_PROBLEMA);
        ProductDetailPage detalle = inventario.openDetail("Sauce Labs Bike Light");
        assertEquals("Sauce Labs Bike Light", detalle.name());
    }

    @Test
    @DisplayName("CP-39 error_user: ordenar no muestra alertas de error y reordena")
    void cp39OrdenErrorUser() {
        InventoryPage inventario = entrarComo(Datos.USUARIO_ERROR);
        String alerta = inventario.sortByAndReadAlert("za");
        assertNull(alerta, "La página mostró la alerta: " + alerta);
        List<String> esperado = new ArrayList<>(Datos.NOMBRES);
        esperado.sort(Collections.reverseOrder());
        assertEquals(esperado, inventario.productNames());
    }

    @Test
    @DisplayName("CP-40 error_user: se pueden agregar los 6 productos al carrito")
    void cp40AgregarErrorUser() {
        InventoryPage inventario = entrarComo(Datos.USUARIO_ERROR);
        List<String> sinCambio = new ArrayList<>();
        for (String slug : Datos.SLUGS) {
            if (!inventario.tryAddProduct(slug)) sinCambio.add(slug);
        }
        assertTrue(sinCambio.isEmpty(), "Add to cart no cambió a Remove en: " + sinCambio);
        assertEquals("6", inventario.cartCount());
    }

    @Test
    @DisplayName("CP-41 visual_user: los precios coinciden con el catálogo")
    void cp41PreciosVisualUser() {
        InventoryPage inventario = entrarComo(Datos.USUARIO_VISUAL);
        assertEquals(Datos.PRECIOS, inventario.productPrices());
    }

    @Test
    @DisplayName("CP-43 performance_glitch_user: el inventario se muestra en 2 s o menos (criterio del equipo)")
    void cp43RendimientoUsuarioLento() {
        long inicio = System.nanoTime();
        abrirLogin().login(Datos.USUARIO_LENTO, Config.password()).waitForProducts();
        long ms = (System.nanoTime() - inicio) / 1_000_000;
        assertTrue(ms <= 2_000, "El inventario tardó " + ms + " ms (criterio: 2000 ms)");
    }
}
