# Requerimientos de carrito y compra

| ID | Requerimiento | Criterios de aceptación numerados |
|---|---|---|
| RF-COM-01 | El usuario autenticado puede agregar un producto al carrito desde el inventario. | 1. Al pulsar “Add to cart”, el producto aparece en el carrito. 2. El botón cambia a “Remove”. 3. El indicador muestra 1. |
| RF-COM-02 | El usuario puede agregar varios productos y retirar artículos antes del checkout. | 1. Cada artículo agregado incrementa el indicador en uno. 2. Retirar un artículo lo elimina de la lista y decrementa el indicador. 3. Al quedar vacío, el indicador no muestra una cantidad positiva. |
| RF-COM-03 | El carrito muestra los productos seleccionados y permite continuar comprando o iniciar checkout. | 1. Se listan nombre y precio de cada producto agregado. 2. “Continue Shopping” regresa al inventario conservando el carrito. 3. “Checkout” abre el formulario de datos. |
| RF-COM-04 | El checkout solicita nombre, apellido y código postal antes de mostrar el resumen. | 1. Con campos completos se avanza al resumen. 2. Si falta el nombre, se muestra un mensaje que identifica el dato. 3. Si falta el apellido o código postal, se indica el primer dato faltante. |
| RF-COM-05 | El resumen refleja los artículos, subtotal, impuesto y total. | 1. Los artículos del resumen coinciden con el carrito. 2. Subtotal equivale a la suma de precios. 3. Total equivale a subtotal más impuesto con redondeo monetario a dos decimales. |
| RF-COM-06 | El usuario puede confirmar una compra de demostración y recibir confirmación. | 1. “Finish” completa la compra. 2. Se muestra un mensaje de confirmación. 3. La confirmación solo aparece después de datos válidos y resumen. |

## Revisión estática

| ID | Claridad | Ambigüedad/completitud | Verificabilidad | Ajuste recomendado |
|---|---|---|---|---|
| RF-COM-01 | Alta | “Aparece” requiere definir lista/contador; se especifica en criterios | Alta | Ninguno |
| RF-COM-02 | Alta | Definir comportamiento exacto al llegar a cero | Alta | Criterio 3 aclara ausencia de badge positivo |
| RF-COM-03 | Alta | No fija orden de artículos; no es necesario para alcance | Alta | Ninguno |
| RF-COM-04 | Alta | No se define formato o validación del código postal | Alta para campos vacíos; formato pendiente | Agregar regla de formato si el producto la exige |
| RF-COM-05 | Media | Impuesto puede depender de configuración y redondeo | Alta con tolerancia monetaria | Confirmar tasa/estrategia con especificación oficial del curso |
| RF-COM-06 | Alta | “Confirmación” se define como mensaje visible; persistencia no incluida | Alta | Ninguno |

Alcance total de requerimientos del equipo indicado por la guía: 8–12. Esta sección aporta seis requerimientos; deben integrarse con los de Persona 1 sin duplicados.
