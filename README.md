# Swag Labs

Proyecto de pruebas de calidad de software para el flujo de carrito y compra de [Swag Labs](https://www.saucedemo.com/). Incluye automatización con Java 17, Maven, Selenium 4 y JUnit 5, documentación del plan, requerimientos, casos, técnicas de diseño y una guía de ejecución.

## Requisitos previos

- JDK 17 o superior y Maven 3.9 o superior.
- Chrome instalado (predeterminado), o Firefox/Edge instalado si se cambia `-Dbrowser`.
- Conexión a Internet para abrir el sitio. Selenium Manager intenta resolver el controlador del navegador en la primera ejecución.

## Ejecutar

Desde esta carpeta:

```powershell
mvn test
```

Ejecución sin ventana:

```powershell
mvn -Pheadless test
```

Elegir navegador o dirección:

```powershell
mvn test -Dbrowser=firefox
mvn test -Dbrowser=edge -DbaseUrl=https://www.saucedemo.com/
```

Configura las credenciales de prueba en las variables de entorno de la sesión (no se guardan credenciales en el repositorio):

```powershell
$env:SWAGLABS_TEST_USER = "tu_usuario_demo"
$env:SWAGLABS_TEST_PASSWORD = "tu_clave_demo"
mvn test
```

Resultados JUnit XML: `target/surefire-reports/`. Reporte HTML de la última corrida: `target/site/surefire-report.html`. Para regenerar el reporte después de ejecutar pruebas:

```powershell
mvn org.apache.maven.plugins:maven-surefire-report-plugin:3.5.2:report-only
```

El reporte muestra resultados de una ejecución real; si vuelves a correr la suite, regenera el HTML para actualizarlo.

## Estructura

```text
src/test/java/proyecto/pages/   Page Objects (pantallas y acciones)
src/test/java/proyecto/tests/   Pruebas JUnit
docs/                           plan, requerimientos, casos y técnicas
README.md                       instrucciones del proyecto
EXPLICACION.txt                 qué hace cada función y por qué se eligió
```

## Estado de ejecución

La última corrida registrada el 25 de noviembre de 2026 tuvo 15 pruebas aprobadas, sin fallos ni errores, en 74.36 segundos. El detalle de las corridas está en `docs/ejecucion.md`. Los resultados XML se generan en `target/surefire-reports/` al ejecutar las pruebas; `target/` es una carpeta temporal de Maven.

La documentación incluida en este repositorio está en `docs/`. La comprobación adicional en la interfaz fue asistida desde navegador y se identifica por separado; si el curso requiere que cada estudiante ejecute los casos manualmente, registre su propia corrida y tiempo.

## Alcance y notas

Las pruebas automatizadas cubren 15 escenarios del carrito y checkout con una cuenta de demostración configurada localmente. No se guardan credenciales ni datos personales en el repositorio; los casos usan datos ficticios. No se automatizan compras de verdad ni se envían datos reales. Los localizadores se basan en los identificadores `data-test`/`id` habituales del sitio; si el sitio cambia, actualizar el Page Object correspondiente.
