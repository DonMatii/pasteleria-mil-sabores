package com.grupo8.apppasteleriamilsabores.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.grupo8.apppasteleriamilsabores.ui.components.MilBottomNav
import com.grupo8.apppasteleriamilsabores.ui.components.MilTopBar
import com.grupo8.apppasteleriamilsabores.ui.theme.CafeOscuro
import com.grupo8.apppasteleriamilsabores.ui.theme.CremePastel
import com.grupo8.apppasteleriamilsabores.viewmodel.ContactViewModel

@Composable
fun ContactScreen(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    // ViewModel para gestionar el envío de mensajes de contacto
    val contactVm = remember { ContactViewModel() }

    // Estados para los campos del formulario de contacto
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var showConfirmation by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MilTopBar(title = "📞 Contáctanos")
        },
        bottomBar = {
            MilBottomNav(currentRoute, onNavigate)
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .background(CremePastel)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            // Título y descripción de la sección de contacto
            Text(
                "¿Tienes alguna pregunta o sugerencia?",
                style = MaterialTheme.typography.headlineSmall,
                color = CafeOscuro,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                "Estamos aquí para ayudarte. Completa el formulario y te responderemos a la brevedad.",
                style = MaterialTheme.typography.bodyMedium,
                color = CafeOscuro.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Campo de nombre - Información personal del usuario
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "Nombre")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(12.dp))

            // Campo de apellido - Información personal del usuario
            OutlinedTextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido") },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "Apellido")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(12.dp))

            // Campo de correo electrónico - Para respuesta
            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo electrónico") },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = "Correo")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(12.dp))

            // Campo de mensaje - Consulta o comentario del usuario
            OutlinedTextField(
                value = mensaje,
                onValueChange = { mensaje = it },
                label = { Text("Tu mensaje") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                singleLine = false,
                maxLines = 5
            )

            Spacer(Modifier.height(24.dp))

            // Botón para enviar el formulario de contacto
            Button(
                onClick = {
                    // Enviar mensaje a Firestore y mostrar confirmación
                    contactVm.sendContactMessage(nombre, apellido, correo, mensaje)
                    showConfirmation = true
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CafeOscuro,
                    contentColor = Color.White
                ),
                enabled = nombre.isNotBlank() && apellido.isNotBlank() &&
                        correo.isNotBlank() && mensaje.isNotBlank()
            ) {
                Text("Enviar mensaje", style = MaterialTheme.typography.labelLarge)
            }

            Spacer(Modifier.height(16.dp))

            // Información de contacto adicional de la pastelería
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "También puedes contactarnos por:",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Icon(Icons.Default.Phone, contentDescription = "Teléfono")
                        Spacer(Modifier.width(8.dp))
                        Text("+56 9 8438 0968", style = MaterialTheme.typography.bodyMedium)
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Icon(Icons.Default.Email, contentDescription = "Email")
                        Spacer(Modifier.width(8.dp))
                        Text("contacto@pasteleriamilsabores.cl", style = MaterialTheme.typography.bodyMedium)
                    }

                    Text(
                        "📍 Dirección: Población Angamos, Pasaje Lautaro, casa 2, Santa Julia, Viña del Mar, Chile",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        // Diálogo de confirmación después de enviar el mensaje
        if (showConfirmation) {
            AlertDialog(
                onDismissRequest = { showConfirmation = false },
                title = { Text("✅ Mensaje enviado") },
                text = {
                    Text("Gracias $nombre, hemos recibido tu mensaje. Te contactaremos pronto al correo $correo.")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showConfirmation = false
                            // Limpiar formulario después de enviar
                            nombre = ""
                            apellido = ""
                            correo = ""
                            mensaje = ""
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CafeOscuro,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
}