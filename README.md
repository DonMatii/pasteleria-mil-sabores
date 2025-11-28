## 🍰 App Pastelería Mil Sabores - Grupo 8

Aplicación móvil desarrollada en Android para la gestión y visualización de productos de pastelería. Proyecto correspondiente a la Evaluación final y examen de Desarrollo de Aplicaciones Móviles.

---

## 📋 Descripción del Proyecto
Solución móvil integral para la pastelería "Mil Sabores" que permite a los usuarios explorar el catálogo de productos, gestionar un carrito de compras, realizar pedidos reales y contactar con la pastelería mediante sistema de autenticación seguro.

---

## 🎯 Contexto del Proyecto (EFT)

Este proyecto corresponde a la **Evaluación Final Transversal (EFT) y examen** del curso **Desarrollo de Aplicaciones Móviles**.  
La aplicación refleja el trabajo acumulado del semestre y cumple con los criterios solicitados:

- Diseño visual estructurado y navegación jerárquica.
- Formularios validados con retroalimentación clara por campo.
- Gestión de estado y separación de lógica e interfaz (MVVM).
- Animaciones funcionales y respuesta dinámica a la interacción.
- Consumo de APIs externas y uso de recursos nativos.
- Pruebas unitarias completas.
- Generación de APK firmado en modo release.
- Documentación técnica detallada.

---

## 🏆 Estado del Proyecto

**✅ Pruebas Unitarias Completadas - 11 tests con 100% de éxito**  
**✅ Código Optimizado - Corrección de warnings y mejoras de calidad**  
**✅ Suite de Testing Profesional - Configuración con Mockito y Corrutinas**  
**✅ Sistema de Compras Real - Órdenes guardadas en Firestore**  
**✅ Sistema de Contacto Funcional - Mensajes en Firestore**  
**✅ Controles de Cantidad en Carrito - Botones +/- funcionales**  
**✅ Control de Autenticación - Solo usuarios autenticados pueden comprar**  
**✅ Experiencia de Usuario Mejorada - Navegación intuitiva entre pantallas**  
**✅ APK Firmada Generada - Configuración completa de firma release**

---

## 📊 Métricas de Calidad

- **11 pruebas unitarias ejecutadas**

- **0 fallas - 100% de éxito**

- **Cobertura: AuthViewModel + CartViewModel**

- **Persistencia real: Órdenes y mensajes en Firestore**

- **Consumo de APIs externas: Spotify WebView + OpenWeatherMap**

---

## 📦 APK FIRMADA - MODO RELEASE

### ✅ Configuración Completada

La aplicación cuenta con configuración de firma automatizada para generar APK listas para producción.


### 🔧 Comandos de Build:

./gradlew clean assembleRelease

---

# La APK se genera en:
app/build/outputs/apk/release/app-release.apk

---

## 🔒 Información de Firma:

- Keystore: Configurado en app/build.gradle.kts

- Alias: PasteleriaMilSabores

- APK de salida: app-release.apk lista para distribución

## 🛠 Tecnologías Implementadas

* Lenguaje de programación: Kotlin
* Interfaz de usuario: Jetpack Compose
* Diseño: Material Design 3
* Arquitectura: MVVM (Model-View-ViewModel)
* Base de datos local: Room Database
* Base de datos en la nube: Firebase Firestore
* Autenticación: Firebase Authentication
* Navegación: Navigation Component
* Gestión de estado: StateFlows + ViewModel
* Testing: JUnit + Mockito + Coroutines Test
* Consumo de APIs: Retrofit + Gson
* APIs externas: Spotify Embed API + OpenWeatherMap API

## 🎵 Funcionalidades Multimedia y APIs Externas

**🎧 Integración Spotify** - WebView con playlist musical embebida


**🌤️ API Clima OpenWeatherMap** - Clima en tiempo real de Viña del Mar

**👥 Pantalla "Quienes Somos"** - Información del equipo y detalles del proyecto

**📱 Navegación Expandida** - 7 pantallas principales con flujo optimizado

**🎨 Mejoras Visuales** - Ajustes de interfaz y experiencia de usuario mejorada

🏗 Arquitectura del Proyecto
app/src/main/java/com/grupo8/apppasteleriamilsabores/

├── data/ 
│   ├── api/ # Servicios de API (WeatherApiService, ApiClient)
│   ├── local/ # Room Database y DAOs
│   ├── model/ # Modelos de datos (Productos, CartLineUi, FirestoreOrder, WeatherResponse)
│   └── repo/ # Patrón Repository
├── viewmodel/ # ViewModels de la aplicación (Auth, Store, Cart, Contact, Weather)
├── ui/
│   ├── screens/ # Pantallas principales (Home, Login, Register, Catalog, Cart, Contact, QuienesSomos)
│   ├── components/ # Componentes reutilizables (MilTopBar, MilBottomNav, ProductCard)
│   ├── nav/ # Configuración de navegación (NavHost, Routes)
│   └── theme/ # Tema de la aplicación (colores corporativos)
├── test/ # 🧪 PRUEBAS UNITARIAS
│ └── viewmodel/
│   ├── AuthViewModelTest.kt 
│   └── CartViewModelTest.kt
└── MainActivity.kt # Actividad principal

# ⚡ Funcionalidades Principales

**🔐 Sistema de Autenticación** con email y contraseña mediante Firebase Auth

**👤 Registro de nuevos usuarios** con validaciones en tiempo real

**🎭 Modo invitado** para acceso temporal sin registro

**🛒 Carrito de compras** con gestión completa de productos

**📦 Sistema de órdenes reales** guardadas en Firebase Firestore

**📞 Formulario de contacto** con persistencia en Firestore

**🎨 UI/UX profesional** con Material Design 3 y colores corporativos

**➕➖ Controles de cantidad** en carrito para ajustar productos

**🔒 Control de acceso** para compras solo usuarios autenticados

**🌤️ API Clima en tiempo real** - Integración con OpenWeatherMap para Viña del Mar

# 📦 Gestión de Productos

**Catálogo completo de productos** con grid responsivo

**Sección de productos destacados** en pantalla principal

**Persistencia local** con Room Database

**Carga inicial desde JSON** con datos de ejemplo de pastelería

**Agregar al carrito** con un solo clic

**Vista previa en Home** sin funcionalidad de compra directa

# 🛒 Sistema de Compras Real

**🛍️ Carrito de compras funcional** con gestión de productos

**💰 Cálculo automático de totales** en tiempo real

**✅ Finalizar compra** con órdenes guardadas en Firestore

**🗑️ Vaciar carrito** completo o eliminar productos individuales

**📧 Asociación de órdenes** con email del usuario

**🆔 IDs únicos** para tracking de pedidos

**➕➖ Controles de cantidad** para ajustar unidades de productos

**🔒 Protección de compras** solo para usuarios autenticados

# 🔄 Gestión de Cantidades en Carrito

**Botón +** para aumentar cantidad de productos

**Botón -** para disminuir cantidad de productos

**Eliminación automática** cuando cantidad llega a cero

**Actualización en tiempo real** de subtotales y total general

**Persistencia inmediata** en base de datos local

**Sincronización con UI** mediante StateFlows

# 🔐 Control de Autenticación para Compras

**Verificación automática** de estado de autenticación

**Redirección a login** si usuario no autenticado intenta comprar

**Modo invitado** permite navegación pero requiere autenticación para comprar

**Usuarios registrados** acceso completo a todas las funcionalidades

**Protección en navegación** para rutas sensibles

# 📞 Sistema de Contacto

**📝 Formulario completo** (nombre, apellido, correo, mensaje)

**✅ Validaciones en tiempo real** de campos requeridos

**🔥 Persistencia en Firestore** en colección `contact_messages`

**🎉 Confirmación visual** al usuario después del envío

**📧 Respuesta prometida** al correo del usuario

# 🎨 Experiencia de Usuario

**🎯 Navegación fluida** entre 6 pantallas principales

**🎨 Material Design 3** implementado completamente

**🍫 Colores corporativos** (Café Oscuro, Crema Pastel, Rosa)

**📱 Diseño responsivo** para diferentes tamaños de pantalla

**💬 Feedback visual inmediato** con diálogos y mensajes

**🔄 Estados de carga** durante procesos asíncronos

**🏠 Home intuitivo** con productos destacados y redirección al catálogo

**🛒 Carrito mejorado** con controles de cantidad fáciles de usar

## 🧪 Suite de Pruebas Unitarias

### AuthViewModelTest - 4 Pruebas
**✅ Validación de formato de email correcto/incorrecto**

**✅ Validación de campos vacíos en login**

**✅ Patrones de email válidos**

**✅ Lógica de validación de formularios**

### CartViewModelTest - 6 Pruebas
**✅ Agregar productos al carrito**

**✅ Remover productos del carrito**

**✅ Limpiar carrito completo**

**✅ Cálculo correcto de totales**

**✅ Carrito vacío = total cero**

**✅ Cantidad por defecto al agregar productos**

## 🔄 Flujos de Autenticación

**Autenticación tradicional:** Email y contraseña con Firebase Auth

**Registro de usuario:** Creación de nueva cuenta con validaciones

**Modo invitado:** Acceso temporal sin requerir registro

**Gestión de sesiones:** Logout y cambio entre usuarios

**Control de acceso:** Verificación para funcionalidades de compra

## ✅ Validaciones Implementadas

**Validación de formato de email** con expresiones regulares

**Contraseña mínima de 6 caracteres** para registro

**Verificación de campos requeridos** en todos los formularios

**Mensajes de error específicos** y descriptivos para el usuario

**Validación en tiempo real** durante la escritura

**Control de autenticación** para procesos de compra

## 📊 Gestión de Estado

**La aplicación utiliza ViewModels para la gestión del estado:**

**AuthViewModel:** Controla el estado de autenticación y flujos de login/registro

**StoreViewModel:** Gestiona el catálogo y productos destacados

**CartViewModel:** Administra el estado del carrito de compras y procesamiento de órdenes

**ContactViewModel:** Gestiona el envío y almacenamiento de mensajes de contacto

## 🗄️ Persistencia de Datos

### **Almacenamiento Local (Room):**
- Productos del catálogo
- Carrito de compras temporal
- Datos de usuario local
- Cantidades de productos en carrito

### **Almacenamiento en la Nube (Firestore):**
- Órdenes de compra completadas (`orders` collection)
- Mensajes de contacto (`contact_messages` collection)
- Usuarios autenticados (Firebase Auth)

### **Autenticación (Firebase Auth):**
- Registro y login de usuarios
- Sesiones persistentes
- Modo invitado anónimo

## 🚀 Instrucciones de Ejecución

1. **Clonar el repositorio:**


git clone https://github.com/DonMatii/pasteleria-mil-sabores.git

2. Abrir el proyecto en Android Studio.

3. Ejecutar pruebas unitarias:

./gradlew testDebugUnitTest

4. Generar reporte de tests:

**Los reportes se generan en:**

app/build/reports/tests/testDebugUnitTest/index.html

5. Generar APK firmada en modo release:

./gradlew clean assembleRelease

6. Ejecutar la aplicación en dispositivo virtual o físico

7. Probar funcionalidades:

- Registrarse o usar modo invitado

- Explorar catálogo y agregar productos al carrito

- Ajustar cantidades con botones + y - en el carrito

- Finalizar compra (ver órdenes en Firebase Console)

- Enviar mensaje de contacto (ver mensajes en Firebase Console)

## 📈 Historial de Desarrollo

**Fase 1 - Base y Autenticación**
✅ Sistema de autenticación completo con Firebase Auth
✅ Navegación entre pantallas con Navigation Component  
✅ Pruebas unitarias para ViewModels principales

**Fase 2 - Catálogo y Carrito**
✅ Catálogo de productos con Room Database
✅ Carrito de compras funcional con gestión completa
✅ UI/UX profesional con Material Design 3

**Fase 3 - Persistencia Real**
✅ Integración con Firebase Firestore
✅ Sistema de órdenes reales guardadas en la nube
✅ Formulario de contacto con persistencia en Firestore
✅ Colores corporativos aplicados consistentemente

**Fase 4 - Mejoras de UX y Control**
✅ Controles de cantidad en carrito con botones +/-
✅ Protección de compras para usuarios autenticados
✅ Mejora en navegación entre Home y Catálogo
✅ Optimización de interfaz de usuario

**Fase 5 - Multimedia y Experiencia Completa**
✅ Integración WebView de Spotify con playlist musical
✅ Nueva pantalla "Quienes Somos" con información del equipo
✅ Mejoras visuales en múltiples componentes de interfaz
✅ Navegación expandida a 7 pantallas principales

**Fase 6 - APIs Externas y Consumo de Datos**
✅ Integración API OpenWeatherMap para clima en tiempo real
✅ Configuración Retrofit + Gson para consumo de APIs REST
✅ Implementación ViewModel para gestión de estado del clima
✅ Tarjeta informativa del clima en pantalla principal

**Fase 7 - APK Firmada y Release**
✅ Configuración de firma automatizada en build.gradle.kts
✅ Generación de APK release funcional
✅ Documentación de proceso de build

## 🔄 Próximos Objetivos

🚀 **PARA ENTREGA FINAL - COMPLETADO ✅**

- **APK Firmado** - ✅ CONFIGURADO Y GENERADO

- **Consumo de APIs externas** - ✅ OpenWeatherMap + Spotify

- **Pruebas unitarias** - ✅ 11 tests - 100% éxito

- **Documentación técnica** - ✅ README completo

- **Contexto definido** - ✅ Solución real para pastelería

## 🔒 Seguridad y Configuración

### Archivos Excluidos (.gitignore):
- Keystores y archivos de firma (`*.jks`, `*.keystore`)
- Archivos de configuración sensible
- APKs generadas en build/

## 🏗 Dependencias Principales

- UI y Framework
  implementation("androidx.compose.ui:ui")
  implementation("androidx.compose.material3:material3")
  implementation("androidx.navigation:navigation-compose")
  implementation("androidx.compose.ui:ui-text-google-fonts:1.7.5")
  implementation("androidx.compose.material:material-icons-extended:1.7.5")

- Base de datos local
  implementation("androidx.room:room-runtime:2.6.1")
  implementation("androidx.room:room-ktx:2.6.1")
  kapt("androidx.room:room-compiler:2.6.1")

- Firebase - Servicios en la nube
  implementation(platform("com.google.firebase:firebase-bom:34.5.0"))
  implementation("com.google.firebase:firebase-auth-ktx:23.0.0")
  implementation("com.google.firebase:firebase-firestore-ktx:25.0.0")

- APIs y Networking
  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.squareup.retrofit2:converter-gson:2.9.0")
  implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
  implementation("com.google.code.gson:gson:2.10.1")

- Utilidades y extensiones
  implementation("io.coil-kt:coil-compose:2.7.0")  # Carga de imágenes
  implementation("androidx.webkit:webkit:1.8.0")    # WebView para Spotify
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
  implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.4")

- Testing
  testImplementation("junit:junit:4.13.2")
  testImplementation("org.mockito:mockito-core:5.11.0")
  testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
  testImplementation("androidx.arch.core:core-testing:2.2.0")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

## 👥 Integrantes del Proyecto

**Matías Suazo** - Desarrollo móvil & experiencia de usuario
Enfocado en crear una interfaz intuitiva y funcional que haga la experiencia de compra tan dulce como nuestros productos. Implementación completa de frontend, autenticación, carrito de compras y sistema de contacto.

**Álvaro Chávez** - Backend & desarrollo web
Responsable de la infraestructura que soporta nuestra aplicación y la experiencia web complementaria.
