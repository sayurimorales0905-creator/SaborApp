# 🚀 SPRINT 3 — Edición de Catálogo, Pedidos por Mesa y Cierre de Cuenta

## 📋 Ficha General del Sprint 3

| Parámetro | Detalle |
| :--- | :--- |
| **Objetivo del Sprint** | El mozo toma pedidos por mesa y cierra la cuenta con transacciones SQLite. |
| **Duración** | 45 minutos |
| **Fechas** | Inicio: `05/10/2026` — Fin: `05/10/2026` |
| **Puntos Comprometidos** | 16 Puntos |
| **Historias Incluidas** | HU-07, HU-08, HU-09 |

---

## 🗄️ Modelo de Datos del Sprint (DB_VERSION = 2)

Migración mediante `onUpgrade` manteniendo los datos de la v1.

* **`pedidos`**: `id` INTEGER PK · `mesa_numero` INTEGER · `fecha` TEXT · `estado` TEXT (ABIERTO / CERRADO) · `total` REAL
* **`detalle_pedido`**: `id` INTEGER PK · `pedido_id` INTEGER · `plato_id` INTEGER · `plato_nombre` TEXT · `precio_unitario` REAL · `cantidad` INTEGER · `subtotal` REAL

---

## 📝 Sprint Backlog — Historias de Usuario

### 🔹 HU-07: Editar, eliminar y buscar platos

* **Historia de Usuario:**  
  Como **administrador**, quiero **corregir, eliminar y buscar platos**, para **mantener la carta actualizada**.

* **Atributos:**
  * **Prioridad:** Alta
  * **Puntos de Historia:** 3
  * **Prototipo de Referencia:** P1-03 (Lista), P1-04 (Formulario)

* **Criterios de Aceptación (DoD):**
  * **CA1:** Dado que toco un plato de la lista, cuando se abre el formulario, entonces muestra sus datos y el botón dice «Actualizar».
  * **CA2:** Dado que pulso «Eliminar», cuando confirmo en el diálogo, entonces el plato se borra; si tiene pedidos, aparece «No se puede eliminar: tiene pedidos».
  * **CA3:** Dado que escribo en el buscador, cuando cambia el texto, entonces la lista se filtra por nombre (`LIKE`).
  * **CA4:** Dado que marco un plato como no disponible, cuando tomo un pedido, entonces ese plato no aparece para elegir.

* **Tareas Técnicas:**
  * [x] Agregar `obtenerPlatos()`, `actualizarPlato()`, `eliminarPlato()` y búsqueda en `DBHelper.kt`.
  * [x] Modo edición en `PlatoFormActivity` con `putExtra("plato_id")`.
  * [x] `AlertDialog` de confirmación para eliminar.
  * [x] Filtro de búsqueda en tiempo real con `addTextChangedListener`.

* **Estado:** [ ] Por hacer | [ ] En curso | [x] Hecho

---

### 🔹 HU-08: Tomar pedido por mesa

* **Historia de Usuario:**  
  Como **mozo**, quiero **elegir una mesa y agregarle platos con su cantidad**, para **registrar el pedido sin papel**.

* **Atributos:**
  * **Prioridad:** Alta
  * **Puntos de Historia:** 8
  * **Prototipo de Referencia:** P1-05 (Mesas y pedido)

* **Criterios de Aceptación (DoD):**
  * **CA1:** Dado que abro Pedidos, cuando veo la grilla de mesas, entonces las `OCUPADAS` se distinguen por color de las `LIBRES`.
  * **CA2:** Dado que toco una mesa `LIBRE` y agrego el primer plato, cuando guardo, entonces se crea el pedido `ABIERTO` y la mesa pasa a `OCUPADA`.
  * **CA3:** Dado que agrego un plato con cantidad mayor a 0, cuando lo confirmo, entonces aparece con su subtotal y el total se recalcula.
  * **CA4:** Dado que se guarda el pedido, cuando ocurre un error a mitad, entonces no queda nada guardado (transacción).

* **Tareas Técnicas:**
  * [x] Migrar `DBHelper` a `DB_VERSION = 2` con tablas `pedidos` y `detalle_pedido`.
  * [x] Crear métodos para agregar plato al pedido y recalcular total en SQLite.
  * [x] `PedidoActivity`: selección de mesa + Spinner de platos disponibles + campo cantidad + resumen del consumo.

* **Estado:** [ ] Por hacer | [ ] En curso | [x] Hecho

---

### 🔹 HU-09: Cerrar la cuenta de una mesa

* **Historia de Usuario:**  
  Como **mozo**, quiero **cerrar la cuenta de una mesa**, para **cobrar al cliente y liberar la mesa**.

* **Atributos:**
  * **Prioridad:** Alta
  * **Puntos de Historia:** 5
  * **Prototipo de Referencia:** P1-06 (Cuenta de la mesa)

* **Criterios de Aceptación (DoD):**
  * **CA1:** Dado que la mesa tiene un pedido `ABIERTO`, cuando toco «Ver cuenta», entonces veo cada plato, su cantidad, subtotal y el total.
  * **CA2:** Dado que confirmo «Cerrar cuenta», cuando termina, entonces el pedido pasa a `CERRADO`, se guarda el total y la mesa vuelve a `LIBRE` en una sola transacción (`beginTransaction` / `setTransactionSuccessful`).
  * **CA3:** Opción de compartir el desglose del consumo mediante WhatsApp o aplicaciones del sistema.

* **Tareas Técnicas:**
  * [x] Consulta de detalles del pedido y cálculo del total en `DBHelper`.
  * [x] Método `cerrarCuentaMesa(pedidoId, mesaNumero)` con transacción atómica SQLite.
  * [x] `CuentaActivity` con resumen detallado y botones «Cerrar cuenta» y «Compartir por WhatsApp».

* **Estado:** [ ] Por hacer | [ ] En curso | [x] Hecho

---

## 📅 Bitácora Daily Scrum

| Fecha - Hora | ¿Qué hice? | ¿Qué haré hoy? | ¿Qué me bloquea? |
| :--- | :--- | :--- | :--- |
| `05/10/2026 10:00` | Migración de base de datos a `DB_VERSION = 2` y creación de `pedidos` y `detalle_pedido`. | Implementar lógica de adición de ítems y recalculación de total. | Ninguno. |
| `05/10/2026 10:30` | Lógica de transacciones en SQLite para cierre de cuenta y liberación de mesa. | Probar flujo de pedidos completo en emulador y preparar documentación. | Ninguno. |

---

## 🔍 Sprint Review (Demostración)

| Historia | ¿Cumple la DoD? | Evidencia (captura / commit) | Visto bueno PO |
| :--- | :---: | :--- | :---: |
| **HU-07** | [x] Sí  [ ] No | `commit: feat(platos): HU-05/HU-07 - Registrar, listar, buscar, editar y eliminar platos en SQLite` | [x] |
| **HU-08** | [x] Sí  [ ] No | `commit: feat(pedidos): HU-08 - Tomar pedidos por mesa con persistencia en SQLite` | [x] |
| **HU-09** | [x] Sí  [ ] No | `commit: feat(cuenta): HU-09 - Cerrar cuenta con transacciones SQLite y compartir por WhatsApp` | [x] |

---

## 🔄 Retrospectiva del Sprint 3

| ¿Qué funcionó? | ¿Qué mejorar? | Acción para el próximo sprint |
| :--- | :--- | :--- |
| Las transacciones en SQLite aseguraron la consistencia entre pedidos y mesas. | Añadir la impresión o generación de recibos PDF para la cuenta. | Implementar reportes avanzados de ventas y exportación en el Sprint 4. |