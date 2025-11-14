// ProductCard.kt
package com.grupo8.apppasteleriamilsabores.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.grupo8.apppasteleriamilsabores.data.model.Productos

@Composable
fun ProductCard(
    p: Productos,
    onAddToCart: (Long) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(12.dp)) {
            val ctx = LocalContext.current
            val drawableId = p.imagenProd?.let { key ->
                ctx.resources.getIdentifier(key, "drawable", ctx.packageName)
            } ?: 0

            if (drawableId != 0) {
                Image(
                    painter = painterResource(id = drawableId),
                    contentDescription = p.nombreProd,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(160.dp)
                )
                Spacer(Modifier.height(8.dp))
            }

            Text(p.nombreProd, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            Text("$" + "%.0f".format(p.precioProd), style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = { onAddToCart(p.idProd) },
                shape = MaterialTheme.shapes.medium
            ) { Text("Agregar al carrito") }
        }
    }
}

