## 🍰 App Pastelería Mil Sabores - Grupo 8

Aplicación móvil desarrollada en Android para la gestión y visualización de productos de pastelería. Proyecto correspondiente a la Evaluación N°4 de Desarrollo de Aplicaciones Móviles.

**📋 Descripción del Proyecto**
Solución móvil integral para la pastelería "Mil Sabores" que permite a los usuarios explorar el catálogo de productos, gestionar un carrito de compras y acceder mediante sistema de autenticación seguro.

## 🏆 Estado del Proyecto

**✅ Pruebas Unitarias Completadas - 11 tests con 100% de éxito**

**✅ Código Optimizado - Corrección de warnings y mejoras de calidad**

**✅ Suite de Testing Profesional - Configuración con Mockito y Corrutinas**

# 📊 Métricas de Calidad

**✅ 11 pruebas unitarias ejecutadas**

**✅ 0 fallas - 100% de éxito**

**✅ 1.682 segundos de ejecución**

**✅ Cobertura: AuthViewModel + CartViewModel**

# 🛠 Tecnologías Implementadas

**Lenguaje de programación:** Kotlin**

**Interfaz de usuario:** Jetpack Compose

**Diseño:** Material Design 3

**Arquitectura:** MVVM (Model-View-ViewModel)

**Base de datos local:** Room Database

**Autenticación:** Firebase Authentication

**Navegación:** Navigation Component

**Gestión de estado:** StateFlows + ViewModel

**Testing:** JUnit + Mockito + Coroutines Test

# 🏗 Arquitectura del Proyecto

app/src/main/java/com/grupo8/apppasteleriamilsabores/

├── data/
│   ├── local/           # Room Database y DAOs
│   ├── model/           # Modelos de datos
│   └── repo/            # Patrón Repository
├── viewmodel/           # ViewModels de la aplicación
├── ui/
│   ├── screens/         # Pantallas principales
│   ├── components/      # Componentes reutilizables  
│   ├── nav/            # Configuración de navegación
│   └── theme/          # Tema de la aplicación
├── test/               # 🧪 PRUEBAS UNITARIAS
│   └── viewmodel/
│       ├── AuthViewModelTest.kt
│       └── CartViewModelTest.kt
└── MainActivity.kt     # Actividad principal

# ⚡ Funcionalidades Principales

**🔐 Sistema de Autenticación con email y contraseña mediante Firebase Auth**

**Registro de nuevos usuarios con validaciones**

**Modo invitado para acceso temporal**

**Validaciones de formularios en tiempo real**

**Manejo de estados de carga y errores**

### 🎨 **Experiencia de Usuario Mejorada**
- Interfaz unificada con Material Design 3
- Colores consistentes en todos los flujos de autenticación
- Validaciones en tiempo real y mensajes de error descriptivos
- Navegación fluida entre pantallas

# 📦 Gestión de Productos

**Catálogo completo de productos**

**Sección de productos destacados**

**Persistencia local con Room Database**

**Visualización en grid responsivo**

**Carga inicial desde JSON con datos de ejemplo**

# 🛒 Carrito de Compras

**Agregar y eliminar productos del carrito**

**Cálculo automático de totales**

**Persistencia de datos del carrito**

**Funcionalidad para vaciar carrito completo**

# 🎨 Experiencia de Usuario

**Navegación fluida entre pantallas**

**Implementación de Material Design 3**

**Animaciones y transiciones fluidas**

**Tema personalizado con paleta de colores**

**Feedback visual mediante diálogos y notificaciones**

## 🧪 Suite de Pruebas Unitarias

- AuthViewModelTest - 4 Pruebas

**✅ Validación de formato de email correcto/incorrecto**

**✅ Validación de campos vacíos en login**

**✅ Patrones de email válidos**

**✅ Lógica de validación de formularios**

- CartViewModelTest - 6 Pruebas

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

## ✅ Validaciones Implementadas

**Validación de formato de email**

**Contraseña mínima de 6 caracteres**

**Verificación de campos requeridos**

**Mensajes de error específicos y descriptivos**

## 📊 Gestión de Estado

**La aplicación utiliza ViewModels para la gestión del estado:**

**AuthViewModel:** Controla el estado de autenticación y flujos de login/registro

**StoreViewModel:** Gestiona el catálogo y productos destacados

**CartViewModel:** Administra el estado del carrito de compras

## 🚀 Instrucciones de Ejecución

1. Clonar el repositorio:

git clone https://github.com/DonMatii/pasteleria-mil-sabores.git

2. Abrir el proyecto en Android Studio.

3. Ejecutar pruebas unitarias:

./gradlew testDebugUnitTest

4. Generar reporte de tests:

**Los reportes se generan en:** app/build/reports/tests/testDebugUnitTest/index.html

5. Ejecutar la aplicación en dispositivo virtual o físico

## 📈 Historial de Desarrollo

- Pruebas Unitarias & Calidad de Código

**✅ Implementación de 11 pruebas unitarias**

**✅ Configuración de entorno de testing profesional**

**✅ Mocking de dependencias con Mockito**

**✅ Testing de corrutinas con Test Dispatchers**

**✅ Corrección de warnings y mejoras de código**

**✅ Optimización de parsing JSON en MainActivity**

- Funcionalidades Principales

**✅ Sistema de autenticación completo**

**✅ Gestión de productos y carrito**

**✅ Navegación entre pantallas**

**✅ Persistencia de datos local**

## 🔄 Próximos Objetivos

**🚀 Próxima Entrega**

**📦 APK Firmado** - Generación de versión release

**📚 Documentación Técnica** - Arquitectura y diagramas

**🔗 Integración Spring Boot** - Cuando el backend esté listo en AWS

- **Mejoras UI/UX**: Optimización de pantallas principales (Home, Login, Register) con Material Design 3

## 🏗 Dependencias de Testing

testImplementation("junit:junit:4.13.2")
testImplementation("org.mockito:mockito-core:5.11.0")
testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
testImplementation("androidx.arch.core:core-testing:2.2.0")
testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

## 👥 Integrantes del Proyecto

**Matías Suazo** - Desarrollo móvil & experiencia de usuario
Enfocado en crear una interfaz intuitiva y funcional que haga la experiencia de compra tan dulce como nuestros productos.

**Álvaro Chávez** - Backend & desarrollo web
Responsable de la infraestructura que soporta nuestra aplicación y la experiencia web complementaria.


