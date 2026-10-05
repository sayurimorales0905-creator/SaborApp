# 🚀 SPRINT 1 — App Navegable e Identidad Visual

## 📋 Ficha Técnica del Sprint 1

| Parámetro | Detalle |
| :--- | :--- |
| **Objetivo del Sprint** | App navegable: login validado, menú y pantallas del restaurante (sin datos). |
| **Duración** | 30 minutos |
| **Fechas** | Inicio: `05/10/2026` — Fin: `05/10/2026` |
| **Puntos Comprometidos** | 5 Puntos |
| **Historias Incluidas** | HU-01, HU-02, HU-03 |

---

## 📝 Sprint Backlog — Historias de Usuario

### 🔹 HU-01: Pantalla de inicio de sesión

* **Historia de Usuario:**  
  Como **mozo**, quiero **ingresar con usuario y contraseña**, para que **solo el personal autorizado use la app**.

* **Atributos:**
  * **Prioridad:** Alta
  * **Puntos de Historia:** 2
  * **Prototipo de Referencia:** P1-01 (Login)

* **Criterios de Aceptación (DoD):**
  * **CA1:** Dado que los campos están vacíos, cuando pulso «Ingresar», entonces se muestra un mensaje de error debajo de cada campo vacío.
  * **CA2:** Dado que escribo `admin` / `1234`, cuando pulso «Ingresar», entonces se abre el menú principal y el login se cierra (presionar "atrás" no regresa al login).
  * **CA3:** Dado que escribo credenciales incorrectas, cuando pulso «Ingresar», entonces aparece el Toast «Credenciales incorrectas».
  * **CA4:** Dado que escribo la contraseña, cuando la veo en pantalla, entonces aparece oculta y puedo mostrarla activando el ícono de ojo.

* **Tareas Técnicas:**
  * [x] Crear el proyecto Android `SaborApp` (`Empty Views Activity`, Kotlin, Min SDK API 26) con paquete `com.senati.saborapp`.
  * [x] Activar `viewBinding` en `build.gradle.kts` (Module `:app`).
  * [x] Diseñar `activity_login.xml` con `TextInputLayout` (usuario, contraseña con `password_toggle`) y botón de ingreso.
  * [x] Programar la validación en `LoginActivity.kt`.
  * [x] Declarar `LoginActivity` como `LAUNCHER` en `AndroidManifest.xml`.

* **Estado:** [ ] Por hacer | [ ] En curso | [x] Hecho

---

### 🔹 HU-02: Menú principal y navegación

* **Historia de Usuario:**  
  Como **mozo**, quiero **un menú con Platos, Mesas, Pedidos y Reportes**, para **llegar rápido a cada función de la app**.

* **Atributos:**
  * **Prioridad:** Alta
  * **Puntos de Historia:** 2
  * **Prototipo de Referencia:** P1-02 (Menú principal)

* **Criterios de Aceptación (DoD):**
  * **CA1:** Dado que inicié sesión, cuando se abre el menú, entonces veo las opciones *Platos*, *Mesas*, *Pedidos*, *Reportes* y el botón «Salir».
  * **CA2:** Dado que estoy en el menú, cuando toco una opción, entonces se abre su pantalla y con «atrás» vuelvo al menú.
  * **CA3:** Dado que estoy en el menú, cuando toco «Salir», entonces regreso al login.
  * **CA4:** Dado que inicio sesión como `MOZO`, cuando veo el menú, entonces la opción *Reportes* no aparece (exclusivo para `ADMIN`).

* **Tareas Técnicas:**
  * [x] Renombrar `MainActivity` a `MenuActivity` y crear una Activity por opción (`PlatosActivity`, `MesasActivity`, `PedidoActivity`, `ReportesActivity`).
  * [x] Diseñar `activity_menu.xml` con cuatro botones Material con ícono.
  * [x] Programar los `Intents` de navegación en `MenuActivity.kt`.

* **Estado:** [ ] Por hacer | [ ] En curso | [x] Hecho

---

### 🔹 HU-03: Identidad visual del negocio

* **Historia de Usuario:**  
  Como **dueño del negocio**, quiero **que la app tenga el nombre, colores e ícono de mi empresa**, para **que se vea profesional ante mis clientes y trabajadores**.

* **Atributos:**
  * **Prioridad:** Media (Should)
  * **Puntos de Historia:** 1
  * **Prototipo de Referencia:** P1-02

* **Criterios de Aceptación (DoD):**
  * **CA1:** Dado que instalo la app, cuando veo el launcher, entonces aparece el nombre «SaborApp» con un ícono propio.
  * **CA2:** Dado que abro cualquier pantalla, cuando la observo, entonces usa la paleta cálida (naranja y rojo) definida en `colors.xml` y `themes.xml`.
  * **CA3:** Dado que reviso el código, cuando busco textos fijos en los layouts, entonces todos están en `strings.xml`.

* **Tareas Técnicas:**
  * [x] Definir la paleta cálida (naranja `#DF4C1E` y rojo `#C62828`) en `colors.xml` y aplicarla en `themes.xml`.
  * [x] Crear el ícono del sistema con *Image Asset* (`res` → `New` → `Image Asset`).
  * [x] Pasar todos los textos de los layouts a `strings.xml`.

* **Estado:** [ ] Por hacer | [ ] En curso | [x] Hecho

---

## 📅 Bitácora Daily Scrum

| Fecha - Hora | ¿Qué hice? | ¿Qué haré hoy? | ¿Qué me bloquea? |
| :--- | :--- | :--- | :--- |
| `05/10/2026 08:00` | Creación del proyecto en Android Studio e inicialización de Git. | Configurar ViewBinding y maquetar `activity_login.xml`. | Ninguno. |
| `05/10/2026 08:15` | Validación de Login en `LoginActivity.kt` y navegación inicial. | Crear vistas para el menú, aplicar estilos y colores en `themes.xml`. | Ninguno. |

---

## 🔍 Sprint Review (Demostración)

| Historia | ¿Cumple la DoD? | Evidencia (captura / commit) | Visto bueno PO |
| :--- | :---: | :--- | :---: |
| **HU-01** | [x] Sí  [ ] No | `commit: feat(login): HU-01 - Pantalla de inicio de sesión con validaciones y ViewBinding` | [x] |
| **HU-02** | [x] Sí  [ ] No | `commit: feat(menu): HU-02 - Menú principal y navegación entre pantallas del restaurante` | [x] |
| **HU-03** | [x] Sí  [ ] No | `commit: style(theme): HU-03 - Identidad visual, paleta cálida y recursos del sistema` | [x] |

---

## 🔄 Retrospectiva del Sprint 1

| ¿Qué funcionó? | ¿Qué mejorar? | Acción para el próximo sprint |
| :--- | :--- | :--- |
| La navegación basada en `Intents` y ViewBinding se implementó rápidamente. | Organizar de antemano los recursos visuales e íconos. | Tener el script de SQLite diseñado antes de iniciar la codificación del Sprint 2. |