# AppPasteleriaMilSabores_Grupo8

Aplicación móvil desarrollada en Android Studio con Kotlin y Jetpack Compose, siguiendo la arquitectura MVVM. Este proyecto corresponde a la asignatura de Desarrollo de Aplicaciones Móviles y tiene como objetivo implementar una solución que permita gestionar y visualizar información relacionada con la pastelería Mil Sabores.

## Descripción

La aplicación presenta una interfaz sencilla e intuitiva, diseñada para ofrecer una experiencia de usuario fluida. En esta versión se ha implementado:

- **Organización en paquetes**: model, viewmodel, ui, ui.theme, data.local, data.repo
- **Pantallas principales**: Home, Catálogo, Carrito, Login y Registro
- **Base de datos local**: Room Database para persistencia de datos
- **Animaciones fluidas**: Mejora de experiencia de usuario
- **Configuración básica de Firebase**: Preparada para integración futura

## ✨ Animaciones Implementadas

1. **Fade-in suave**: Las tarjetas de productos aparecen gradualmente (800ms)
2. **Pulsación de botones**: Efecto táctil al agregar productos al carrito
3. **Toast de confirmación pastel**: Notificación estilo "dulce" al agregar items

## Tecnologías y Herramientas

- **Lenguaje**: Kotlin
- **Framework UI**: Jetpack Compose
- **Arquitectura**: MVVM (Model - View - ViewModel)
- **Base de datos**: Room Database
- **Navegación**: Navigation Component
- **Animaciones**: Compose Animation APIs
- **Entorno de desarrollo**: Android Studio
- **Control de versiones**: Git y GitHub

## Estructura del Proyecto
app/src/main/java/com/grupo8/apppasteleriamilsabores
│
├── data
│ ├── local # Room Database, DAOs y entidades
│ └── repo # Repositorio y lógica de datos
│
├── model # Clases de datos y modelos
├── viewmodel # Lógica de presentación y manejo de estado
├── ui # Pantallas y componentes visuales
│ ├── components # Componentes reutilizables
│ ├── screens # Pantallas de la aplicación
│ ├── nav # Navegación y rutas
│ └── theme # Colores, tipografía y estilos globales
│
└── MainActivity.kt

text

## Funcionalidades Actuales

- ✅ Navegación entre pantallas
- ✅ Catálogo de productos con base de datos local
- ✅ Carrito de compras con persistencia
- ✅ Sistema de autenticación (registro/login)
- ✅ Animaciones fluidas y atractivas
- ✅ Diseño responsivo con Material Design 3
- ✅ Tema personalizado con colores pastel

## Ejecución del Proyecto

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/DonMatii/pasteleria-mil-sabores.git
Abrir el proyecto en Android Studio.

Configurar un dispositivo virtual en Device Manager.

Ejecutar la aplicación con el botón Run.

Integrantes del Grupo 8
Matías Suazo

Álvaro Chávez

Estado del Proyecto
Versión funcional con características completas:

✅ Estructura MVVM completa

✅ Navegación entre pantallas implementada

✅ Base de datos local con Room

✅ Sistema de carrito de compras

✅ Autenticación de usuarios

✅ Animaciones mejoradas

✅ Configuración Firebase lista

Próximas mejoras:

Integración con Firebase Authentication

Sync con Cloud Firestore