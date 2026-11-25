package proyecto.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import proyecto.base.BaseTest;
import proyecto.base.Datos;
import proyecto.pages.InventoryPage;
import proyecto.pages.ProductDetailPage;

/** Inventario, ordenamiento y detalle con standard_user (RF-INV-01 y RF-INV-02). Casos CP-30 a CP-35. */
class InventoryTest extends BaseTest {
    private InventoryPage inventario;

    @BeforeEach
    void entrar() {
        inventario = entrarComo(Datos.USUARIO_ESTANDAR);
    }

    @Test
    @DisplayName("CP-30 El inventario muestra 6 productos con nombre, precio e imagen distinta")
    void cp30ListadoCompleto() {
        assertEquals(6, inventario.productCount());
        assertEquals(Datos.NOMBRES, inventario.productNames());
        assertEquals(Datos.PRECIOS, inventario.productPrices());
        assertEquals(6, new HashSet<>(inventario.productImageFiles()).size(), "cada producto tiene su propia imagen");
    }

    @Test
    @DisplayName("CP-31 Ordenar por nombre de A a Z")
    void cp31OrdenAZ() {
        inventario.sortBy("za");   // se cambia primero para comprobar que A-Z realmente reordena
        inventario.sortBy("az");
        List<String> esperado = new ArrayList<>(Datos.NOMBRES);
        Collections.sort(esperado);
        assertEquals(esperado, inventario.productNames());
    }

    @Test
    @DisplayName("CP-32 Ordenar por nombre de Z a A")
    void cp32OrdenZA() {
        inventario.sortBy("za");
        List<String> esperado = new ArrayList<>(Datos.NOMBRES);
        esperado.sort(Collections.reverseOrder());
        assertEquals(esperado, inventario.productNames());
    }

    @Test
    @DisplayName("CP-33 Ordenar por precio de menor a mayor (frontera: $7.99 primero, $49.99 último)")
    void cp33PrecioAscendente() {
        inventario.sortBy("lohi");
        assertEquals(List.of(7.99, 9.99, 15.99, 15.99, 29.99, 49.99), inventario.productPrices());
    }

    @Test
    @DisplayName("CP-34 Ordenar por precio de mayor a menor (frontera: $49.99 primero, $7.99 último)")
    void cp34PrecioDescendente() {
        inventario.sortBy("hilo");
        assertEquals(List.of(49.99, 29.99, 15.99, 15.99, 9.99, 7.99), inventario.productPrices());
    }

    @Test
    @DisplayName("CP-35 El detalle muestra el producto elegido y se puede volver al inventario")
    void cp35DetalleDeProducto() {
        ProductDetailPage detalle = inventario.openDetail("Sauce Labs Backpack");
        assertEquals("Sauce Labs Backpack", detalle.name());
        assertEquals(29.99, detalle.price(), 0.001);
        assertTrue(detalle.url().contains("inventory-item.html?id=4"));
        assertEquals(6, detalle.backToProducts().waitForProducts().productCount());
    }
}
