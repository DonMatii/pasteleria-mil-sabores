package com.grupo8.apppasteleriamilsabores.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import kotlinx.coroutines.delay

@Composable
fun CartScreen(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    lines: List<CartLineUi>,
    onRemove: (Long) -> Unit,
    onClear: () -> Unit,
    onPurchase: () -> Unit  // Función para procesar compra
) {
    // Estado para controlar mensaje de agradecimiento
    var showThankYouMessage by remember { mutableStateOf(false) }

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
            // Estado del carrito - vacío o con productos
            if (lines.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "Tu carrito está vacío 🍰",
                        style = MaterialTheme.typography.titleMedium,
                        color = CafeOscuro.copy(alpha = 0.7f)
                    )
                }
            } else {
                // Lista de productos en el carrito
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

                // Separador visual antes del total
                HorizontalDivider(
                    Modifier
                        .padding(vertical = 12.dp)
                        .background(Rosa.copy(alpha = 0.3f))
                        .height(2.dp)
                )

                // Sección de total de la compra
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

                // Botón principal - Finalizar compra con color corporativo
                Button(
                    onClick = {
                        showThankYouMessage = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CafeOscuro,  // Color café chocolate corporativo
                        contentColor = Color.White    // Texto blanco para contraste
                    )
                ) {
                    Text("✅ Finalizar Compra", style = MaterialTheme.typography.labelLarge)
                }

                Spacer(Modifier.height(8.dp))

                // Botón secundario - Vaciar carrito completo con mismo color corporativo
                Button(
                    onClick = onClear,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CafeOscuro,  // Mismo color café chocolate
                        contentColor = Color.White    // Texto blanco para contraste
                    )
                ) {
                    Text("🗑️ Vaciar carrito", style = MaterialTheme.typography.labelLarge)
                }
            }
        }

        // Mensaje de agradecimiento después de comprar
        if (showThankYouMessage) {
            // Efecto para controlar el tiempo del mensaje y la navegación
            LaunchedEffect(showThankYouMessage) {
                delay(5000) // Mostrar por 5 segundos COMPLETOS
                showThankYouMessage = false
                // Solo después de mostrar el mensaje, procesar la compra
                onPurchase()
            }

            // Overlay con mensaje de agradecimiento
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f)) // Fondo más oscuro
                    .padding(32.dp), // Más padding para mejor visibilidad
                contentAlignment = Alignment.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = CremePastel,  // Fondo crema pastel corporativo
                        contentColor = CafeOscuro      // Texto café chocolate
                    ),
                    elevation = CardDefaults.cardElevation(12.dp), // Más elevación
                    modifier = Modifier.fillMaxWidth(0.8f) // Ancho del 80%
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp), // Más padding interno
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "🎉 ¡Gracias por su compra!",
                            style = MaterialTheme.typography.headlineMedium, // Texto más grande
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            "Su pedido ha sido procesado exitosamente",
                            style = MaterialTheme.typography.bodyLarge, // Texto más grande
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            "🍰 ¡Disfrute de sus deliciosos pasteles!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = CafeOscuro.copy(alpha = 0.8f)
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            "⏰ Redirigiendo al inicio...",
                            style = MaterialTheme.typography.bodySmall,
                            color = CafeOscuro.copy(alpha = 0.6f)
                        )
                    }
                }
            }
        }
    }
}