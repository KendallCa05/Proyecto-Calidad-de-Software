# Registro de ejecución

## Identificación de corrida

Automatización: Windows 11, Chrome 158.0.8127.88, Java 26.0.2.1 con compilación `--release 17`, Maven 3.9.16 incluido con IntelliJ IDEA 2026.2.2, Selenium 4.27.0, JUnit 5.11.4, URL `https://www.saucedemo.com/`. La hora del reporte de Maven fue 2026-11-25 11:17 CST.

La comprobación por interfaz se realizó el 2026-11-25 con navegador del entorno interactivo y una cuenta de demostración. Esta interacción asistida no sustituye una ejecución manual que el estudiante deba realizar personalmente si así lo exige el curso.

## Resultados manuales

| Caso | Estado | Evidencia/defecto | Observaciones |
|---|---|---|---|
| CP-01 | Pasó | Contador=1; botón cambió a Remove | Interfaz |
| CP-02 | Pasó | Contador=2 al agregar mochila y luz | Interfaz |
| CP-03 | Pasó | Al retirar mochila desapareció el indicador y volvió Add to cart | Interfaz |
| CP-04 | Pasó | Se listaron dos productos en el carrito | Interfaz |
| CP-05 | Pasó | Al retirar mochila quedaron 0 filas y sin indicador | Interfaz |
| CP-06 | Pasó | Continue Shopping volvió a Products y conservó contador=1 | Interfaz |
| CP-07 | Pasó | Nombre vacío mostró “First Name is required” | Interfaz |
| CP-08 | Pasó | Apellido vacío mostró “Last Name is required” | Interfaz |
| CP-09 | Pasó | Código postal vacío mostró “Postal Code is required” | Interfaz |
| CP-10 | Pasó | Datos válidos abrieron Overview con 1 artículo | Interfaz |
| CP-11 | Pasó | Subtotal $29.99, impuesto $2.40, total $32.39 | Interfaz |
| CP-12 | Pasó | 29.99 + 2.40 = 32.39 | Interfaz |
| CP-13 | Pasó | Finalizar mostró “Thank you for your order!” | Interfaz |
| CP-14 | Pasó | Overview mostró 2 artículos y subtotal $39.98 | Interfaz |
| CP-15 | Pasó | Cancel regresó a Your Cart y conservó 1 artículo | Interfaz |

Estados permitidos: **Pasó**, **Falló**, **Bloqueado**, **No ejecutado**. No marcar resultados supuestos.

## Resultados automatizados

| Corrida | Navegador | Resultado | Reporte |
|---|---|---|---|
| Corrida inicial, 2026-11-25 | Chrome 158, Windows 11, Java 26.0.2 | 15 ejecutadas: 4 pasaron, 4 fallaron, 7 con error | Errores de espera y estado en el arnés; todavía no se atribuyen a la aplicación |
| Segunda corrida, 2026-11-25 | Chrome 158, Windows 11, Java 26.0.2 | 15 ejecutadas: 3 pasaron, 1 falló, 11 con error | CP-11 usaba el subtotal desactualizado de $32.99; se corrigió a $29.99 y se ajustaron esperas |
| Comprobación posterior al ajuste | Chrome no disponible en el entorno de comprobación | No se pudo iniciar ninguna prueba: Selenium indicó que faltaba `chrome.exe` en su caché | El código de pruebas compiló; falta repetir la corrida desde IntelliJ en el equipo del usuario |
| Última corrida compartida, 2026-11-25 | Chrome 158, Windows 11, Java 26.0.2 | **15 pasaron, 0 fallaron, 0 errores, 0 omitidos** | Duración: 74.36 s. Build SUCCESS. XML: `target/surefire-reports/TEST-proyecto.tests.CheckoutTest.xml` |

### Detalle de la corrida inicial

| Caso | Estado observado |
|---|---|
| CP-01 | Pasó |
| CP-02 | Falló: contador quedó en 1 al agregar dos productos |
| CP-03 | Pasó |
| CP-04 | Falló: el carrito se consultó con 0 artículos |
| CP-05 | Falló: el carrito seguía mostrando 1 tras retirar |
| CP-06 | Falló: siguió en “Your Cart” al validar regreso al inventario |
| CP-07 | Error al encontrar el botón Continue |
| CP-08 | Pasó |
| CP-09 | Error al encontrar el formulario de datos |
| CP-10 | Pasó |
| CP-11 | Error al encontrar el formulario de datos |
| CP-12 | Error al encontrar el formulario de datos |
| CP-13 | Error al encontrar el formulario de datos |
| CP-14 | Error al encontrar el formulario de datos |
| CP-15 | Error al encontrar el botón Cancel |

Se añadieron esperas explícitas en las transiciones y cambios del carrito. Los resultados de esta corrección quedan pendientes de reejecución en el equipo con Chrome disponible.

### Segunda corrida compartida

La segunda corrida detectó un dato esperado incorrecto: la mochila cuesta **$29.99**, no $32.99. Se cambió el precio esperado y las pantallas sincronizan por su dirección (`inventory.html`, `cart.html`, `checkout-step-one.html`) y luego por los controles que necesitan. La última corrida confirmó **15/15 aprobadas**. El reporte HTML se generó a partir del resultado JUnit XML de esa corrida.

## Defectos

No se confirmó ningún defecto funcional en los 15 casos ejecutados: los resultados observados coincidieron con los criterios. Las fallas de las dos primeras corridas correspondieron a esperas/localizadores del código de automatización y al precio esperado del test. No se registran como defectos de Swag Labs. Si el equipo necesita cinco defectos para el trabajo común, debe obtenerlos al probar otros alcances o usuarios y documentarlos con evidencia, sin convertir oportunidades de mejora en defectos confirmados.

## Inspección de usabilidad

Se revisaron carrito y formulario de checkout con las heurísticas de Nielsen. Se observó que el carrito comunica el número de artículos y permite retirar, continuar o avanzar. El formulario ofrece Cancel y mensajes precisos al faltar un dato. Se recomiendan dos mejoras de prioridad baja: indicar visualmente los pasos/progreso del checkout y marcar como obligatorios los campos antes del primer intento de avance. Estas son oportunidades de diseño, no defectos funcionales observados.

## Métricas por completar

- Defectos por severidad: 0 confirmados en el alcance probado (crítica 0, alta 0, media 0, baja 0).
- Automatización: 15 de 15 casos de este paquete ejecutados en la última corrida (100%).
- Tiempo automatizado: 74.36 s para 15 casos. Tiempo manual comparable: no medido; la interacción de interfaz asistida no representa un cronometraje manual del estudiante.
