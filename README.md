## 🍰 App Pastelería Mil Sabores - Grupo 8

Aplicación móvil desarrollada en Android para la gestión y visualización de productos de pastelería. Proyecto correspondiente a la Evaluación N°4 de Desarrollo de Aplicaciones Móviles.

**📋 Descripción del Proyecto**
Solución móvil integral para la pastelería "Mil Sabores" que permite a los usuarios explorar el catálogo de productos, gestionar un carrito de compras, realizar pedidos reales y contactar con la pastelería mediante sistema de autenticación seguro.

## 🏆 Estado del Proyecto

**✅ Pruebas Unitarias Completadas - 11 tests con 100% de éxito**

**✅ Código Optimizado - Corrección de warnings y mejoras de calidad**

**✅ Suite de Testing Profesional - Configuración con Mockito y Corrutinas**

**✅ Sistema de Compras Real - Órdenes guardadas en Firestore**

**✅ Sistema de Contacto Funcional - Mensajes en Firestore**

# 📊 Métricas de Calidad

**✅ 11 pruebas unitarias ejecutadas**

**✅ 0 fallas - 100% de éxito**

**✅ 1.682 segundos de ejecución**

**✅ Cobertura: AuthViewModel + CartViewModel**

**✅ Persistencia real: Órdenes y mensajes en Firestore**

# 🛠 Tecnologías Implementadas

**Lenguaje de programación:** Kotlin

**Interfaz de usuario:** Jetpack Compose

**Diseño:** Material Design 3

**Arquitectura:** MVVM (Model-View-ViewModel)

**Base de datos local:** Room Database

**Base de datos en la nube:** Firebase Firestore

**Autenticación:** Firebase Authentication

**Navegación:** Navigation Component

**Gestión de estado:** StateFlows + ViewModel

**Testing:** JUnit + Mockito + Coroutines Test

# 🏗 Arquitectura del Proyecto

app/src/main/java/com/grupo8/apppasteleriamilsabores/

├── data/
│   ├── local/           # Room Database y DAOs
│   ├── model/           # Modelos de datos (Productos, CartLineUi, FirestoreOrder)
│   └── repo/            # Patrón Repository
├── viewmodel/           # ViewModels de la aplicación (Auth, Store, Cart, Contact)
├── ui/
│   ├── screens/         # Pantallas principales (Home, Login, Register, Catalog, Cart, Contact)
│   ├── components/      # Componentes reutilizables (MilTopBar, MilBottomNav, ProductCard)
│   ├── nav/            # Configuración de navegación (NavHost, Routes)
│   └── theme/          # Tema de la aplicación (colores corporativos)
├── test/               # 🧪 PRUEBAS UNITARIAS
│   └── viewmodel/
│       ├── AuthViewModelTest.kt
│       └── CartViewModelTest.kt
└── MainActivity.kt     # Actividad principal

# ⚡ Funcionalidades Principales

**🔐 Sistema de Autenticación** con email y contraseña mediante Firebase Auth

**👤 Registro de nuevos usuarios** con validaciones en tiempo real

**🎭 Modo invitado** para acceso temporal sin registro

**🛒 Carrito de compras** con gestión completa de productos

**📦 Sistema de órdenes reales** guardadas en Firebase Firestore

**📞 Formulario de contacto** con persistencia en Firestore

**🎨 UI/UX profesional** con Material Design 3 y colores corporativos

# 📦 Gestión de Productos

**Catálogo completo de productos** con grid responsivo

**Sección de productos destacados** en pantalla principal

**Persistencia local** con Room Database

**Carga inicial desde JSON** con datos de ejemplo de pastelería

**Agregar al carrito** con un solo clic

# 🛒 Sistema de Compras Real

**🛍️ Carrito de compras funcional** con gestión de productos

**💰 Cálculo automático de totales** en tiempo real

**✅ Finalizar compra** con órdenes guardadas en Firestore

**🗑️ Vaciar carrito** completo o eliminar productos individuales

**📧 Asociación de órdenes** con email del usuario

**🆔 IDs únicos** para tracking de pedidos

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

## ✅ Validaciones Implementadas

**Validación de formato de email** con expresiones regulares

**Contraseña mínima de 6 caracteres** para registro

**Verificación de campos requeridos** en todos los formularios

**Mensajes de error específicos** y descriptivos para el usuario

**Validación en tiempo real** durante la escritura

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

5. Ejecutar la aplicación en dispositivo virtual o físico

6. Probar funcionalidades:

- Registrarse o usar modo invitado

- Explorar catálogo y agregar productos al carrito

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

## 🔄 Próximos Objetivos

🚀 Para Entrega Final:

- 📦 APK Firmado - Generación de versión release

- 📚 Documentación Técnica - Arquitectura y diagramas

- 🎤 Preparación Defensa - Demostración de funcionalidades

## 🔮 Futuras Mejoras:

**📋 Historial de pedidos para usuarios registrados**

**📊 Dashboard administrativo en Firebase**

**📧 Notificaciones push de confirmación**

**🔗 Integración Spring Boot cuando backend esté listo**

## 🏗 Dependencias Principales

-  UI y Framework

implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material3:material3")
implementation("androidx.navigation:navigation-compose")

- Base de datos

implementation("androidx.room:room-runtime")
implementation("androidx.room:room-ktx")

- Firebase

implementation("com.google.firebase:firebase-auth-ktx")
implementation("com.google.firebase:firebase-firestore-ktx")

- Testing

testImplementation("junit:junit:4.13.2")
testImplementation("org.mockito:mockito-core:5.11.0")
testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

## 👥 Integrantes del Proyecto

**Matías Suazo** - Desarrollo móvil & experiencia de usuario
Enfocado en crear una interfaz intuitiva y funcional que haga la experiencia de compra tan dulce como nuestros productos. Implementación completa de frontend, autenticación, carrito de compras y sistema de contacto.

**Álvaro Chávez** - Backend & desarrollo web
Responsable de la infraestructura que soporta nuestra aplicación y la experiencia web complementaria.