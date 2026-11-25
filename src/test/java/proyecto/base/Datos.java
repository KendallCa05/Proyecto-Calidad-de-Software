package proyecto.base;

import java.util.List;

/** Catálogo esperado de Swag Labs con standard_user, en el orden por defecto (nombre A-Z). */
public final class Datos {
    public static final List<String> NOMBRES = List.of(
            "Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt",
            "Sauce Labs Fleece Jacket", "Sauce Labs Onesie", "Test.allTheThings() T-Shirt (Red)");
    public static final List<Double> PRECIOS = List.of(29.99, 9.99, 15.99, 49.99, 7.99, 15.99);
    public static final List<String> SLUGS = List.of(
            "sauce-labs-backpack", "sauce-labs-bike-light", "sauce-labs-bolt-t-shirt",
            "sauce-labs-fleece-jacket", "sauce-labs-onesie", "test.allthethings()-t-shirt-(red)");

    public static final String USUARIO_ESTANDAR = "standard_user";
    public static final String USUARIO_PROBLEMA = "problem_user";
    public static final String USUARIO_ERROR = "error_user";
    public static final String USUARIO_VISUAL = "visual_user";
    public static final String USUARIO_LENTO = "performance_glitch_user";

    private Datos() { }
}
