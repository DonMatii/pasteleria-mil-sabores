package com.grupo8.apppasteleriamilsabores.ui.nav

sealed class Route(val path: String) {
    object Home: Route("home")
    object Catalogo: Route("catalogo")
    object Carrito: Route("carrito")
    object Login: Route("login")
    object Registro: Route("registro")
    object Contacto: Route("contacto")
    object QuienesSomos: Route("quienes-somos")
    data class DetalleProd(val id: Long): Route("producto/{id}") {
        companion object { const val template = "producto/{id}"}
    }
}