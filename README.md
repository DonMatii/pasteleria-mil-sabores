# 🍰 App Pastelería Mil Sabores - Grupo 8

Aplicación móvil desarrollada en Android para la gestión y visualización de productos de pastelería. Proyecto correspondiente a la Evaluación Parcial 4 de Desarrollo de Aplicaciones Móviles.

## 📋 Descripción del Proyecto

Solución móvil integral para la pastelería "Mil Sabores" que permite a los usuarios explorar el catálogo de productos, gestionar un carrito de compras y acceder mediante sistema de autenticación seguro.

## 🛠 Tecnologías Implementadas

- **Lenguaje de programación**: Kotlin
- **Interfaz de usuario**: Jetpack Compose
- **Diseño**: Material Design 3
- **Arquitectura**: MVVM (Model-View-ViewModel)
- **Base de datos local**: Room Database
- **Autenticación**: Firebase Authentication
- **Navegación**: Navigation Component
- **Gestión de estado**: StateFlows + ViewModel

## 🏗 Arquitectura del Proyecto
app/src/main/java/com/grupo8/apppasteleriamilsabores/
├── data/
│ ├── local/ # Room Database y DAOs
│ ├── model/ # Modelos de datos
│ └── repo/ # Patrón Repository
├── viewmodel/ # ViewModels de la aplicación
├── ui/
│ ├── screens/ # Pantallas principales
│ ├── components/ # Componentes reutilizables
│ ├── nav/ # Configuración de navegación
│ └── theme/ # Tema de la aplicación
└── MainActivity.kt # Actividad principal

text

## ⚡ Funcionalidades Principales

### 🔐 Sistema de Autenticación
- Autenticación con email y contraseña mediante Firebase Auth
- Registro de nuevos usuarios con validaciones
- Modo invitado para acceso temporal
- Validaciones de formularios en tiempo real
- Manejo de estados de carga y errores

### 📦 Gestión de Productos
- Catálogo completo de productos
- Sección de productos destacados
- Persistencia local con Room Database
- Visualización en grid responsivo

### 🛒 Carrito de Compras
- Agregar y eliminar productos del carrito
- Cálculo automático de totales
- Persistencia de datos del carrito
- Funcionalidad para vaciar carrito completo

### 🎨 Experiencia de Usuario
- Navegación fluida entre pantallas
- Implementación de Material Design 3
- Animaciones y transiciones fluidas
- Tema personalizado con paleta de colores
- Feedback visual mediante diálogos y notificaciones

## 🔄 Flujos de Autenticación

1. **Autenticación tradicional**: Email y contraseña con Firebase Auth
2. **Registro de usuario**: Creación de nueva cuenta con validaciones
3. **Modo invitado**: Acceso temporal sin requerir registro

### ✅ Validaciones Implementadas
- Validación de formato de email
- Contraseña mínima de 6 caracteres
- Verificación de campos requeridos
- Mensajes de error específicos y descriptivos

## 📊 Gestión de Estado

La aplicación utiliza ViewModels para la gestión del estado:

- **AuthViewModel**: Controla el estado de autenticación y flujos de login/registro
- **StoreViewModel**: Gestiona el catálogo y productos destacados
- **CartViewModel**: Administra el estado del carrito de compras

## 🚀 Instrucciones de Ejecución

1. Clonar el repositorio:

git clone https://github.com/DonMatii/pasteleria-mil-sabores.git

2. Abrir el proyecto en Android Studio

3. Configurar dispositivo virtual o conectar dispositivo físico

4. Ejecutar la aplicación

## 📈 Estado de Desarrollo
**✅ Funcionalidades Completadas**
- Implementación de Material Design 3
- Validación de formularios
- Sistema de navegación
- Gestión de estado con ViewModel
- Almacenamiento local con Room
- Integración con Firebase Authentication
- Arquitectura preparada para integración con microservicios

## 🔄 Próximas Implementaciones
- Pruebas unitarias
- Generación de APK firmado
- Documentación técnica completa
- Integración con backend Spring Boot en AWS EC2
- Sistema de gestión de pedidos

## 👥 Integrantes del Proyecto
**Matías Suazo** - Desarrollo móvil & experiencia de usuario
Enfocado en crear una interfaz intuitiva y funcional que haga la experiencia de compra tan dulce como nuestros productos.

**Álvaro Chávez** - Backend & desarrollo web
Responsable de la infraestructura que soporta nuestra aplicación y la experiencia web complementaria.

**Proyecto académico - Evaluación Parcial 3 y examen - Desarrollo de Aplicaciones Móviles**