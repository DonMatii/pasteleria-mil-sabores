// ProductCard.kt - Versión con control de visibilidad del botón
package com.grupo8.apppasteleriamilsabores.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.grupo8.apppasteleriamilsabores.data.model.Productos
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import kotlinx.coroutines.delay

/**
 * Componente reutilizable para mostrar tarjetas de productos
 * @param p Producto a mostrar
 * @param onAddToCart Función callback para agregar al carrito
 * @param showAddButton Controla si muestra el botón "Agregar al carrito" (true) o solo vista previa (false)
 */
@Composable
fun ProductCard(
    p: Productos,
    onAddToCart: (Long) -> Unit,
    showAddButton: Boolean = true  // ✅ PARÁMETRO NUEVO: Controlar visibilidad del botón
) {
    var isVisible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 800)
    )

    // Animación del botón
    var buttonClicked by remember { mutableStateOf(false) }
    val buttonScale by animateFloatAsState(
        targetValue = if (buttonClicked) 0.9f else 1f,
        animationSpec = tween(durationMillis = 150)
    )

    // Animación de confirmación pastel
    var showConfirmation by remember { mutableStateOf(false) }
    val confirmationAlpha by animateFloatAsState(
        targetValue = if (showConfirmation) 1f else 0f,
        animationSpec = tween(durationMillis = 300)
    )

    // Efecto para animación de entrada
    LaunchedEffect(Unit) {
        isVisible = true
    }

    // Resetear animación del botón
    LaunchedEffect(buttonClicked) {
        if (buttonClicked) {
            delay(150)
            buttonClicked = false
        }
    }

    // Ocultar confirmación después de mostrarse
    LaunchedEffect(showConfirmation) {
        if (showConfirmation) {
            delay(2000) // Mostrar por 2 segundos
            showConfirmation = false
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        Card(
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .alpha(alpha)
        ) {
            Column(Modifier.padding(12.dp)) {
                val ctx = LocalContext.current
                val drawableId = p.imagenProd?.let { key ->
                    ctx.resources.getIdentifier(key, "drawable", ctx.packageName)
                } ?: 0

                // Imagen del producto
                if (drawableId != 0) {
                    Image(
                        painter = painterResource(id = drawableId),
                        contentDescription = p.nombreProd,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(160.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                }

                // Información del producto
                Text(
                    p.nombreProd,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    "$" + "%.0f".format(p.precioProd),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(8.dp))

                // ✅ MODIFICADO: Mostrar botón solo si showAddButton es true
                // En Home será false, en Catálogo será true
                if (showAddButton) {
                    Button(
                        onClick = {
                            buttonClicked = true
                            showConfirmation = true  // Mostrar confirmación
                            onAddToCart(p.idProd)
                        },
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.scale(buttonScale)
                    ) {
                        Text("Agregar al carrito")
                    }
                }
            }
        }

        // Mensaje de confirmación pastel - solo mostrar si el botón está visible
        if ((showConfirmation || confirmationAlpha > 0f) && showAddButton) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(confirmationAlpha)
                    .padding(8.dp)
                    .align(Alignment.TopCenter)
            ) {
                Card(
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(
                        containerColor = androidx.compose.ui.graphics.Color(0xFFFFE4E9), // Rosa pastel
                        contentColor = androidx.compose.ui.graphics.Color(0xFF8B4513)    // Café oscuro
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            "🎂",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            "¡Agregado al carrito!",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}