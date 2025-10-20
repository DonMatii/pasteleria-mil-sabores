package com.grupo8.apppasteleriamilsabores.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grupo8.apppasteleriamilsabores.ui.components.HeroBanner
import com.grupo8.apppasteleriamilsabores.ui.components.MilBottomNav
import com.grupo8.apppasteleriamilsabores.ui.components.MilTopBar

@Composable
fun HomeScreen(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onGoCatalog: () -> Unit
) {
    Scaffold(
        topBar = { MilTopBar(title = "Pastelería Mil Sabores", onCart = { onNavigate("carrito") }) },
        bottomBar = { MilBottomNav(currentRoute, onNavigate) }
    ) { inner ->
        Column(
            modifier = Modifier.fillMaxSize().padding(inner).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeroBanner(title = "Endulza tu día", subtitle = "Productos destacados del mes")
            Spacer(Modifier.height(20.dp))
            Button(onClick = onGoCatalog) { Text("Ver catálogo") }
        }
    }
}

