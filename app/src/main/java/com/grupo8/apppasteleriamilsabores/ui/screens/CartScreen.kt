package com.grupo8.apppasteleriamilsabores.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.grupo8.apppasteleriamilsabores.data.model.CartLineUi
import com.grupo8.apppasteleriamilsabores.ui.components.MilBottomNav
import com.grupo8.apppasteleriamilsabores.ui.components.MilTopBar
import com.grupo8.apppasteleriamilsabores.ui.theme.CafeOscuro
import com.grupo8.apppasteleriamilsabores.ui.theme.CremePastel
import com.grupo8.apppasteleriamilsabores.ui.theme.Rosa

@Composable
fun CartScreen(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    lines: List<CartLineUi>,
    onRemove: (Long) -> Unit,
    onClear: () -> Unit
) {
    Scaffold(
        topBar = {
            MilTopBar(title = "🛍️ Carrito de compras")
        },
        bottomBar = {
            MilBottomNav(currentRoute, onNavigate)
        }
    ) { inner ->
        val total = lines.sumOf { it.subtotal }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .background(CremePastel)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            if (lines.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "Tu carrito está vacío 🍰",
                        style = MaterialTheme.typography.titleMedium,
                        color = CafeOscuro.copy(alpha = 0.7f)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(lines) { line ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 2.dp),
                            elevation = CardDefaults.cardElevation(6.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = line.nombreProd,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = CafeOscuro,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "x${line.cantidadProd}  ·  $${"%.0f".format(line.precioProd)} c/u",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = CafeOscuro.copy(alpha = 0.7f)
                                    )
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        "$${"%.0f".format(line.subtotal)}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Medium,
                                        color = CafeOscuro
                                    )
                                    TextButton(
                                        onClick = { onRemove(line.idProd) },
                                        contentPadding = PaddingValues(0.dp)
                                    ) {
                                        Text(
                                            "Eliminar",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = Rosa
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Divider(
                    Modifier
                        .padding(vertical = 12.dp)
                        .background(Rosa.copy(alpha = 0.3f))
                        .height(2.dp)
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Total",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = CafeOscuro
                    )
                    Text(
                        "$${"%.0f".format(total)}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Rosa
                    )
                }

                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = onClear,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Rosa,
                        contentColor = Color.White
                    )
                ) {
                    Text("Vaciar carrito", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}

