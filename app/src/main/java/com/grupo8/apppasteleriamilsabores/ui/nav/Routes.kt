package com.grupo8.apppasteleriamilsabores.ui.nav

/**
 * Sistema de rutas para la navegación de la aplicación
 * Define todas las pantallas disponibles en Pastelería Mil Sabores
 */
sealed class Route(val path: String) {
    // Pantalla principal con productos destacados
    object Home: Route("home")

    // Catálogo completo de productos disponibles
    object Catalogo: Route("catalogo")

    // Carrito de compras con gestión de productos
    object Carrito: Route("carrito")

    // Pantalla de inicio de sesión con Firebase Auth
    object Login: Route("login")

    // Pantalla de registro de nuevos usuarios
    object Registro: Route("registro")

    // Pantalla de contacto para consultas y sugerencias
    object Contacto: Route("contacto")

    // Pantalla de información sobre la pastelería
    object QuienesSomos: Route("quienes-somos")

    // Pantalla de detalles de producto (para futura implementación)
    data class DetalleProd(val id: Long): Route("producto/{id}") {
        companion object {
            const val template = "producto/{id}"
        }
    }
}