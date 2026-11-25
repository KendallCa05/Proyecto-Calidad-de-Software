# Contexto del producto y plan de pruebas

## Producto y alcance

Swag Labs (Sauce Demo) es una tienda de demostración. Este paquete cubre agregar y quitar artículos, indicador del carrito, datos de comprador, resumen de compra, cálculo de impuesto/total y confirmación. No procesa pagos reales.

## Ambiente de referencia

| Elemento | Configuración propuesta |
|---|---|
| Dirección | `https://www.saucedemo.com/` |
| Navegador | Chrome estable; compatibilidad adicional con Firefox y Edge configurable |
| Sistema operativo | Windows 10/11 (registrar edición y compilación al ejecutar); también adaptable a macOS/Linux |
| Resolución | 1366×768 o mayor; maximizada en automatización |
| Datos | Cuenta de demostración configurada localmente; comprador ficticio `Test Buyer`, código postal de prueba `00000` |
| Herramientas | Java 17+, Maven 3.9+, Selenium 4.27.0, JUnit 5.11.4 |

Registrar las versiones realmente utilizadas; las anteriores son la configuración esperada del proyecto, no una medición del equipo donde se entregue.

## Tipos y niveles

| Tipo/nivel | Aplicación | Justificación |
|---|---|---|
| Pruebas funcionales de sistema | Casos CP-01 a CP-15, de interfaz hasta confirmación | Comprueba el comportamiento integrado que observa el usuario. |
| Pruebas de integración | Interacción carrito → checkout → resumen → confirmación | Asegura que los datos y artículos se conserven entre pantallas. |
| Pruebas de aceptación | Flujo válido y mensajes de validación | Contrasta resultados con los criterios de aceptación del producto. |
| Pruebas de regresión | Reejecutar los 15 casos luego de cambios | Detecta roturas en acciones centrales de compra. |
| Usabilidad heurística | Revisión manual con heurísticas de Nielsen en escritorio | Permite detectar problemas de visibilidad, prevención de errores y consistencia. |
| Compatibilidad | Ejecutar suite en Chrome, Firefox y Edge | Identifica diferencias de renderizado o interacción entre motores. |

## Criterios de entrada

- Sitio accesible; usuario demo válido.
- Java, Maven y navegador elegidos instalados; acceso de red permitido.
- Casos, datos y versión de navegador identificados.

## Criterios de salida

- Todos los casos con estado documentado (pasó, falló, bloqueado o no ejecutado).
- Cada fallo reproducible tiene incidencia con pasos, resultado esperado/obtenido y evidencia.
- Se registra el resultado de automatización y el reporte generado; no se declara salida aprobada si hay defectos críticos/altos abiertos.
- Se documentan limitaciones, versiones y cobertura pendiente.

## Procedimiento no funcional: inspección heurística

1. Abrir el sitio en Chrome y Firefox con tamaño de escritorio y una cuenta de demostración configurada en la máquina de prueba.
2. Recorrer inventario, carrito, datos, resumen y confirmación.
3. Revisar visibilidad del estado, correspondencia con el mundo real, control del usuario, consistencia, prevención/recuperación de errores, reconocimiento y accesibilidad básica.
4. Registrar cada hallazgo con pantalla, heurística, pasos, impacto, captura y severidad sugerida.
5. Repetir con un tamaño móvil para comprobar adaptación, si forma parte de la entrega.

### Resultados

**Pendiente de ejecución.** No se presentan hallazgos como observados porque no se realizó una inspección interactiva de la aplicación en esta entrega. Completar navegador/versión, fecha, pasos y evidencia después de ejecutar el procedimiento. Esta separación evita inventar resultados.
