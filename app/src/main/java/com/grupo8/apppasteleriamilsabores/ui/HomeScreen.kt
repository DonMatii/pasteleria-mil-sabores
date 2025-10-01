package com.grupo8.apppasteleriamilsabores.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // margen adaptable
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Bienvenido a Pastelería Mil Sabores",
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // ocupa espacio proporcional
        )

        Button(
            onClick = { /* acción */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp) // margen adaptable
        ) {
            Text("Ingresar")
        }

    }
}
