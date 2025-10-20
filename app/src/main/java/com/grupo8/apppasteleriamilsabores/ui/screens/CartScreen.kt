package com.grupo8.apppasteleriamilsabores.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grupo8.apppasteleriamilsabores.data.model.CartItem
import com.grupo8.apppasteleriamilsabores.ui.components.MilBottomNav
import com.grupo8.apppasteleriamilsabores.ui.components.MilTopBar

@Composable
fun CartScreen(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    items: List<CartItem>,
    onRemove: (Long) -> Unit,
    onClear: () -> Unit
) {
    Scaffold(
        topBar = { MilTopBar(title = "Carrito") },
        bottomBar = { MilBottomNav(currentRoute, onNavigate) }
    ) { inner ->
        Column(Modifier.fillMaxSize().padding(inner).padding(16.dp)) {
            LazyColumn(Modifier.weight(1f)) {
                items(items) { it ->
                    Row(
                        Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Producto #${it.productoId} x${it.cantidadProds}")
                        TextButton(onClick = { onRemove(it.id) }) { Text("Eliminar") }
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            Button(onClick = onClear, modifier = Modifier.fillMaxWidth()) { Text("Vaciar carrito") }
        }
    }
}

