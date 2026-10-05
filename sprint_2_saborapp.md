# 🚀 SPRINT 2 — Base de Datos y Catálogos (Platos y Mesas)

## 📋 Ficha General del Sprint 2

| Parámetro | Detalle |
| :--- | :--- |
| **Objetivo del Sprint** | Platos y mesas se registran y listan desde SQLite; login real. |
| **Duración** | 60 minutos |
| **Fechas** | Inicio: `05/10/2026` — Fin: `05/10/2026` |
| **Puntos Comprometidos** | 11 Puntos |
| **Historias Incluidas** | HU-04, HU-05, HU-06 |

---

## 🗄️ Modelo de Datos del Sprint (DB_VERSION = 1)

Base de datos: `saborapp.db`

* **`usuario`**: `id` INTEGER PK · `usuario` TEXT UNIQUE · `clave` TEXT · `rol` TEXT (ADMIN / MOZO)
* **`plato`**: `id` INTEGER PK · `nombre` TEXT NOT NULL · `categoria` TEXT · `precio` REAL CHECK(precio > 0) · `disponible` INTEGER (0/1)
* **`mesa`**: `id` INTEGER PK · `numero` INTEGER UNIQUE · `capacidad` INTEGER · `estado` TEXT (LIBRE / OCUPADA)

---

## 📝 Sprint Backlog — Historias de Usuario

### 🔹 HU-04: Base de datos y login con SQLite

* **Historia de Usuario:**  
  Como **administrador**, quiero **que los usuarios se guarden y validen en la base de datos del celular**, para **no depender de credenciales escritas en el código**.

* **Atributos:**
  * **Prioridad:** Alta
  * **Puntos de Historia:** 3
  * **Prototipo de Referencia:** P1-01 (Login)

* **Criterios de Aceptación (DoD):**
  * **CA1:** Dado que instalo la app por primera vez, cuando se abre, entonces se crea `saborapp.db` con la tabla `usuario` y el usuario `admin` / `1234` (rol `ADMIN`).
  * **CA2:** Dado que ingreso credenciales, cuando pulso «Ingresar», entonces se validan con una consulta parametrizada (`rawQuery` con `?`).
  * **CA3:** Dado que el login es correcto, cuando se abre el menú, entonces muestra el nombre y rol del usuario.
  * **CA4:** Dado que abro App Inspection → Database Inspector, cuando selecciono la BD, entonces veo la tabla `usuario` con sus registros.

* **Tareas Técnicas:**
  * [x] Crear `DBHelper.kt` (`SQLiteOpenHelper`) con `DB_NAME = "saborapp.db"` y `DB_VERSION = 1`.
  * [x] Crear la tabla `usuario` e insertar el admin en `onCreate`.
  * [x] Agregar `validarUsuario(usuario, clave)` y usarla en `LoginActivity`.
  * [x] Verificar la BD en Database Inspector.

* **Estado:** [ ] Por hacer | [ ] En curso | [x] Hecho

---

### 🔹 HU-05: Registrar y listar platos

* **Historia de Usuario:**  
  Como **administrador**, quiero **registrar platos con nombre, categoría, precio y disponibilidad**, para **tener la carta del restaurante en la app**.

* **Atributos:**
  * **Prioridad:** Alta
  * **Puntos de Historia:** 5
  * **Prototipo de Referencia:** P1-03 (Lista de platos), P1-04 (Formulario)

* **Criterios de Aceptación (DoD):**
  * **CA1:** Dado que el nombre o el precio están vacíos, cuando pulso «Guardar», entonces se marca el error y no se guarda.
  * **CA2:** Dado que escribo un precio menor o igual a 0, cuando pulso «Guardar», entonces aparece «Precio inválido».
  * **CA3:** Dado que elijo la categoría en un Spinner (Entradas, Fondos, Bebidas, Postres), cuando guardo, entonces el plato aparece en la lista ordenado por categoría y nombre.
  * **CA4:** Dado que cierro y abro la app, cuando entro a Platos, entonces los platos registrados siguen ahí.

* **Tareas Técnicas:**
  * [x] Crear `Plato.kt` (`data class`) y la tabla `platos`.
  * [x] Crear consultas en `DBHelper.kt` con `insertarPlato()`, `actualizarPlato()`, `eliminarPlato()` y `obtenerPlatos()`.
  * [x] Diseñar vistas en `activity_platos.xml` y `activity_plato_form.xml`.
  * [x] Crear `PlatoFormActivity` con validaciones y Spinner de categorías.

* **Estado:** [ ] Por hacer | [ ] En curso | [x] Hecho

---

### 🔹 HU-06: Registrar y listar mesas

* **Historia de Usuario:**  
  Como **administrador**, quiero **registrar las mesas con su número y capacidad**, para **asignar los pedidos a cada mesa**.

* **Atributos:**
  * **Prioridad:** Alta
  * **Puntos de Historia:** 3
  * **Prototipo de Referencia:** P1-05 (Mesas y pedido)

* **Criterios de Aceptación (DoD):**
  * **CA1:** Dado que registro un número de mesa que ya existe, cuando pulso «Guardar», entonces aparece «La mesa ya existe».
  * **CA2:** Dado que la capacidad no está entre 1 y 12, cuando guardo, entonces se muestra «Capacidad inválida».
  * **CA3:** Dado que registro una mesa nueva, cuando vuelvo a la lista, entonces aparece con estado `LIBRE`.

* **Tareas Técnicas:**
  * [x] Crear la tabla `mesas` con `numero` UNIQUE.
  * [x] Crear métodos en `DBHelper.kt` (`insertarMesa`, `obtenerMesas`).
  * [x] Lista de mesas en `MesasActivity` con cuadrícula de 3 columnas.

* **Estado:** [ ] Por hacer | [ ] En curso | [x] Hecho

---

## 📅 Bitácora Daily Scrum

| Fecha - Hora | ¿Qué hice? | ¿Qué haré hoy? | ¿Qué me bloquea? |
| :--- | :--- | :--- | :--- |
| `05/10/2026 09:00` | Creación de `DBHelper.kt` y tablas de usuarios y platos. | Implementar la autenticación SQLite en `LoginActivity`. | Ninguno. |
| `05/10/2026 09:30` | CRUD de Platos y Mesas con validaciones. | Probar persistencia de datos y Database Inspector. | Ninguno. |

---

## 🔍 Sprint Review (Demostración)

| Historia | ¿Cumple la DoD? | Evidencia (captura / commit) | Visto bueno PO |
| :--- | :---: | :--- | :---: |
| **HU-04** | [x] Sí  [ ] No | `commit: feat(database): HU-04 - Base de datos SQLite DBHelper y autenticación real` | [x] |
| **HU-05** | [x] Sí  [ ] No | `commit: feat(platos): HU-05/HU-07 - Registrar, listar, buscar, editar y eliminar platos en SQLite` | [x] |
| **HU-06** | [x] Sí  [ ] No | `commit: feat(mesas): HU-06 - Registrar y listar mesas en SQLite` | [x] |

---

## 🔄 Retrospectiva del Sprint 2

| ¿Qué funcionó? | ¿Qué mejorar? | Acción para el próximo sprint |
| :--- | :--- | :--- |
| La arquitectura con `SQLiteOpenHelper` funcionó fluidamente. | Manejar mejor los diálogos flotantes para evitar cierres inesperados. | Preparar transacciones SQL complejas para los cierres de cuenta en el Sprint 3. |