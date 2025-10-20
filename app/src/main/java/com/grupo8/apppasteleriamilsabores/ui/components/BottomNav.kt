package com.grupo8.apppasteleriamilsabores.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Person

data class BottomItem(val label: String, val icon: ImageVector, val route: String)

@Composable
fun MilBottomNav(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        BottomItem("Inicio", Icons.Outlined.Home, "home"),
        BottomItem("Catálogo", Icons.Outlined.Menu, "catalogo"),
        BottomItem("Carrito", Icons.Outlined.ShoppingCart, "carrito"),
        BottomItem("Login", Icons.Outlined.Person, "login"),
    )
    NavigationBar(containerColor = MaterialTheme.colorScheme.background) {
        items.forEach { it ->
            NavigationBarItem(
                selected = currentRoute == it.route,
                onClick = { onNavigate(it.route) },
                icon = { Icon(it.icon, contentDescription = it.label) },
                label = { Text(it.label) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.tertiary
                )
            )
        }
    }
}
