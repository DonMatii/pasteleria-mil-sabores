package com.grupo8.apppasteleriamilsabores.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ContactPhone
import androidx.compose.material.icons.outlined.Info // ✅ NUEVO ICONO

// Modelo para los items de la barra de navegación inferior
data class BottomItem(val label: String, val icon: ImageVector, val route: String)

@Composable
fun MilBottomNav(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    // Lista de items para la barra de navegación - define las pantallas disponibles
    val items = listOf(
        BottomItem("Inicio", Icons.Outlined.Home, "home"),           // Pantalla principal
        BottomItem("Catálogo", Icons.Outlined.Menu, "catalogo"),     // Catálogo de productos
        BottomItem("Carrito", Icons.Outlined.ShoppingCart, "carrito"), // Carrito de compras
        BottomItem("Contacto", Icons.Outlined.ContactPhone, "contacto"), // Pantalla de contacto
        BottomItem("Nosotros", Icons.Outlined.Info, "quienes-somos"), // Pantalla Quiénes Somos
        BottomItem("Login", Icons.Outlined.Person, "login")          // Inicio de sesión
    )

    // Barra de navegación inferior - permite navegar entre las pantallas principales
    NavigationBar(containerColor = MaterialTheme.colorScheme.background) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route, // Resaltar item de la ruta actual
                onClick = { onNavigate(item.route) },  // Navegar a la pantalla seleccionada
                icon = { Icon(item.icon, contentDescription = item.label) }, // Icono del item
                label = { Text(item.label) }, // Texto descriptivo del item
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.tertiary // Color de selección
                )
            )
        }
    }
}