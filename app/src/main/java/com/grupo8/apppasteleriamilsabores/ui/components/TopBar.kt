package com.grupo8.apppasteleriamilsabores.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MilTopBar(
    title: String,
    onCart: (() -> Unit)? = null
) {
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        actions = {
            if (onCart != null) {
                TextButton(
                    onClick = onCart,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Carrito", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    )
}


