package com.grupo8.apppasteleriamilsabores.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
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

/**
 * Pantalla del carrito de compras - Muestra productos seleccionados y gestiona compra
 * @param currentRoute Ruta actual de navegación
 * @param onNavigate Función callback para navegar entre pantallas
 * @param lines Lista de productos en el carrito con información de cantidad y subtotal
 * @param onRemove Función callback para eliminar producto del carrito
 * @param onClear Función callback para vaciar carrito completo
 * @param onPurchase Función callback para procesar compra (solo usuarios autenticados)
 * @param isAuthenticated Indica si el usuario está autenticado (logueado o como invitado)
 * @param onUpdateQuantity Función callback para actualizar cantidad de productos
 */
@Composable
fun CartScreen(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    lines: List<CartLineUi>,
    onRemove: (Long) -> Unit,
    onClear: () -> Unit,
    onPurchase: () -> Unit,
    isAuthenticated: Boolean,
    onUpdateQuantity: (Long, Int) -> Unit
) {
    // Estado para controlar mensaje de agradecimiento
    var showThankYouMessage by remember { mutableStateOf(false) }

    // Estado para controlar redirección a login
    var shouldRedirectToLogin by remember { mutableStateOf(false) }

    // Efecto para redirigir a login cuando sea necesario
    LaunchedEffect(shouldRedirectToLogin) {
        if (shouldRedirectToLogin) {
            delay(300)
            onNavigate("login")
        }
    }

    // Efecto para manejar la compra y el mensaje de agradecimiento
    LaunchedEffect(showThankYouMessage) {
        if (showThankYouMessage) {
            delay(5000)
            showThankYouMessage = false
            onPurchase()
        }
    }

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
                                // Columna izquierda: Información del producto
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
                                        text = "$${"%.0f".format(line.precioProd)} c/u",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = CafeOscuro.copy(alpha = 0.7f)
                                    )
                                    Text(
                                        text = "Subtotal: $${"%.0f".format(line.subtotal)}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Rosa,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                // Columna derecha: Controles de cantidad y eliminar
                                Column(
                                    horizontalAlignment = Alignment.End,
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    // Controles de cantidad (+/-)
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        // Botón para disminuir cantidad
                                        IconButton(
                                            onClick = {
                                                val nuevaCantidad = line.cantidadProd - 1
                                                if (nuevaCantidad <= 0) {
                                                    // Si la cantidad llega a 0, eliminar producto
                                                    onRemove(line.idProd)
                                                } else {
                                                    // Actualizar cantidad usando productId
                                                    onUpdateQuantity(line.productId, nuevaCantidad)
                                                }
                                            },
                                            modifier = Modifier.size(32.dp),
                                            colors = IconButtonDefaults.iconButtonColors(
                                                containerColor = Rosa.copy(alpha = 0.2f),
                                                contentColor = Rosa
                                            )
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Remove,
                                                contentDescription = "Disminuir cantidad",
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }

                                        // Cantidad actual
                                        Text(
                                            text = line.cantidadProd.toString(),
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = CafeOscuro,
                                            modifier = Modifier.width(24.dp)
                                        )

                                        // Botón para aumentar cantidad
                                        IconButton(
                                            onClick = {
                                                val nuevaCantidad = line.cantidadProd + 1
                                                // Actualizar cantidad usando productId
                                                onUpdateQuantity(line.productId, nuevaCantidad)
                                            },
                                            modifier = Modifier.size(32.dp),
                                            colors = IconButtonDefaults.iconButtonColors(
                                                containerColor = CafeOscuro.copy(alpha = 0.2f),
                                                contentColor = CafeOscuro
                                            )
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Add,
                                                contentDescription = "Aumentar cantidad",
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }

                                    // Botón eliminar producto
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

                // Botón de compra condicional basado en autenticación
                if (isAuthenticated) {
                    // Botón principal - Finalizar compra para usuarios autenticados
                    Button(
                        onClick = {
                            showThankYouMessage = true
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.large,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CafeOscuro,
                            contentColor = Color.White
                        )
                    ) {
                        Text("✅ Finalizar Compra", style = MaterialTheme.typography.labelLarge)
                    }
                } else {
                    // Botón para redirigir a login si no está autenticado
                    Button(
                        onClick = {
                            shouldRedirectToLogin = true
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.large,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text("🔐 Iniciar Sesión para Comprar", style = MaterialTheme.typography.labelLarge)
                    }

                    // Mensaje informativo
                    Text(
                        "Debes iniciar sesión o entrar como invitado para realizar compras",
                        style = MaterialTheme.typography.bodySmall,
                        color = CafeOscuro.copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(Modifier.height(8.dp))

                // Botón secundario - Vaciar carrito completo
                Button(
                    onClick = onClear,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CafeOscuro,
                        contentColor = Color.White
                    )
                ) {
                    Text("🗑️ Vaciar carrito", style = MaterialTheme.typography.labelLarge)
                }
            }
        }

        // Mensaje de agradecimiento después de comprar
        if (showThankYouMessage && isAuthenticated) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f))
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = CremePastel,
                        contentColor = CafeOscuro
                    ),
                    elevation = CardDefaults.cardElevation(12.dp),
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "🎉 ¡Gracias por su compra!",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            "Su pedido ha sido procesado exitosamente",
                            style = MaterialTheme.typography.bodyLarge,
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