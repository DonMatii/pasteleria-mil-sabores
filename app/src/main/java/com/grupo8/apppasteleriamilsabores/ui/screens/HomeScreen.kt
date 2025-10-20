package com.grupo8.apppasteleriamilsabores.ui.screens

import com.grupo8.apppasteleriamilsabores.ui.components.MilTopBar
import com.grupo8.apppasteleriamilsabores.ui.components.MilBottomNav
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grupo8.apppasteleriamilsabores.data.model.Productos
import com.grupo8.apppasteleriamilsabores.ui.components.ProductCard

@Composable
fun HomeScreen(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onGoCatalog: () -> Unit,
    destacados: List<Productos>           // 👈 NUEVO
) {
    Scaffold(
        topBar = {MilTopBar(title = "Pastelería Mil Sabores", onCart = { onNavigate("carrito") }) },
        bottomBar = { MilBottomNav(currentRoute, onNavigate)  }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text("Productos destacados del mes", style = MaterialTheme.typography.headlineSmall)

            Spacer(Modifier.height(8.dp))

            if (destacados.isEmpty()) {
                Text(
                    "Aún no hay destacados. Revisa el catálogo completo.",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(destacados) { p ->
                        // versión compacta de ProductCard (o usa la misma con width fijo)
                        Card(
                            shape = MaterialTheme.shapes.large,
                            modifier = Modifier.width(240.dp)
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                // Puedes reutilizar tu ProductCard si quieres:
                                // ProductCard(p = p, onAddToCart = { /* no agrega desde home */ })
                                // O una mini-card propia:
                                ProductCard(p = p, onAddToCart = { /* opcional */ })
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Button(onClick = onGoCatalog, modifier = Modifier.fillMaxWidth()) {
                Text("Ver catálogo completo")
            }
        }
    }
}


