package proyecto.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {
    private static final Properties FILE = new Properties();

    static {
        try (InputStream in = Config.class.getResourceAsStream("/config.properties")) {
            if (in != null) FILE.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo leer config.properties", e);
        }
    }

    private Config() { }

    private static String sys(String key) {
        String v = System.getProperty(key);
        return (v == null || v.isBlank() || v.startsWith("${")) ? null : v;
    }

    public static String baseUrl() {
        String v = sys("baseUrl");
        String url = v != null ? v : FILE.getProperty("baseUrl");
        return url.endsWith("/") ? url : url + "/";
    }

    public static String browser() {
        String v = sys("browser");
        return (v != null ? v : FILE.getProperty("browser")).toLowerCase();
    }

    public static boolean headless() {
        String v = sys("headless");
        return Boolean.parseBoolean(v != null ? v : FILE.getProperty("headless"));
    }

    /** Contraseña de demostración: -DtestPassword, SWAGLABS_TEST_PASSWORD o config.properties. */
    public static String password() {
        String v = sys("testPassword");
        if (v == null) v = System.getenv("SWAGLABS_TEST_PASSWORD");
        return (v != null && !v.isBlank()) ? v : FILE.getProperty("password");
    }
}
