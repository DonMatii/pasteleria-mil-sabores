# AppPasteleriaMilSabores_Grupo8

Aplicación móvil desarrollada en Android Studio con Kotlin y Jetpack Compose, siguiendo la arquitectura MVVM.  
Este proyecto corresponde a la asignatura de Desarrollo de Aplicaciones Móviles y tiene como objetivo implementar una solución que permita gestionar y visualizar información relacionada con la pastelería Mil Sabores.

## Descripción

La aplicación presenta una interfaz sencilla e intuitiva, diseñada para ofrecer una experiencia de usuario fluida.  
En esta primera versión se ha configurado la estructura base del proyecto, incluyendo:

- Organización en paquetes: model, viewmodel, ui y ui.theme.
- Pantalla inicial (HomeScreen) con mensaje de bienvenida.
- Configuración de emulador en Device Manager para pruebas.

## Tecnologías y Herramientas

- Lenguaje: Kotlin
- Framework UI: Jetpack Compose
- Arquitectura: MVVM (Model - View - ViewModel)
- Entorno de desarrollo: Android Studio
- Control de versiones: Git y GitHub

## Estructura del Proyecto

app/src/main/java/com/grupo8/apppasteleriamilsabores
│
├── model # Clases de datos y modelos
├── viewmodel # Lógica de presentación y manejo de estado
├── ui # Pantallas y componentes visuales
│
└── theme # Colores, tipografía y estilos globales
└── MainActivity.kt


## Ejecución del Proyecto

1. Clonar el repositorio:

git clone https://github.com/DonMatii/pasteleria-mil-sabores.git

2. Abrir el proyecto en Android Studio.
3. Configurar un dispositivo virtual en Device Manager.
4. Ejecutar la aplicación con el botón Run.

## Integrantes del Grupo 8

- Matías Suazo
- Álvaro Chávez


## Estado del Proyecto

Versión inicial con estructura MVVM y pantalla de bienvenida funcional.  
Próximos pasos:
- Implementar navegación entre pantallas.
- Añadir repositorio y modelos de datos.
- Mejorar la interfaz con componentes personalizados.
