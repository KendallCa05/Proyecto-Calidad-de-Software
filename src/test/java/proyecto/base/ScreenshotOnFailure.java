package proyecto.base;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotOnFailure implements TestExecutionExceptionHandler {
    private static final List<String[]> FALLOS = new ArrayList<>(); // {nombre, archivo, mensaje}

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable error) throws Throwable {
        WebDriver driver = BaseTest.currentDriver();
        if (driver instanceof TakesScreenshot shooter) {
            try {
                Path dir = Paths.get("target", "screenshots");
                Files.createDirectories(dir);
                String archivo = context.getDisplayName().replaceAll("[^A-Za-z0-9._-]+", "_") + ".png";
                Files.write(dir.resolve(archivo), shooter.getScreenshotAs(OutputType.BYTES));
                registrar(context.getDisplayName(), archivo, String.valueOf(error.getMessage()));
            } catch (IOException | RuntimeException ignored) {
            }
        }
        throw error;
    }

    private static synchronized void registrar(String nombre, String archivo, String mensaje) throws IOException {
        FALLOS.add(new String[] {nombre, archivo, mensaje});
        StringBuilder html = new StringBuilder("<!DOCTYPE html><html lang=\"es\"><head><meta charset=\"UTF-8\">"
                + "<title>Capturas de pruebas fallidas</title><style>body{font-family:sans-serif;margin:24px}"
                + "section{border:1px solid #ccc;padding:12px;margin-bottom:16px}img{max-width:100%;border:1px solid #999}"
                + "pre{white-space:pre-wrap}</style></head><body><h1>Capturas de pruebas fallidas</h1>");
        for (String[] f : FALLOS) {
            html.append("<section><h2>").append(esc(f[0])).append("</h2><pre>").append(esc(f[2])).append("</pre>")
                .append("<img src=\"../screenshots/").append(esc(f[1])).append("\" alt=\"Captura de ").append(esc(f[0]))
                .append("\"></section>");
        }
        html.append("</body></html>");
        Path out = Paths.get("target", "site");
        Files.createDirectories(out);
        Files.writeString(out.resolve("capturas-fallos.html"), html.toString());
    }

    private static String esc(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
}
