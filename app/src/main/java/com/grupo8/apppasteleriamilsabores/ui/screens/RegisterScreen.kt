package com.grupo8.apppasteleriamilsabores.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.grupo8.apppasteleriamilsabores.ui.components.MilBottomNav
import com.grupo8.apppasteleriamilsabores.ui.components.MilTopBar
import com.grupo8.apppasteleriamilsabores.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    vm: AuthViewModel,
    onRegistered: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Observar estados
    val authError by vm.authError.collectAsState()
    val isLoading by vm.isLoading.collectAsState()

    // Efecto para limpiar errores cuando se cambian los campos
    LaunchedEffect(name, apellido, email, pass) {
        if (authError != null) {
            vm.clearError()
        }
    }

    Scaffold(
        topBar = { MilTopBar(title = "Crear cuenta") },
        bottomBar = { MilBottomNav(currentRoute, onNavigate) }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp)
        ) {
            Text(
                "Crear nueva cuenta",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Mostrar errores
            authError?.let { error ->
                if (error.isNotBlank()) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Warning,
                                contentDescription = "Error",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = error,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                isError = authError != null
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                isError = authError != null
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                isError = authError != null
            )
            Spacer(Modifier.height(8.dp))

            // Campo de contraseña con opción mostrar/ocultar
            OutlinedTextField(
                value = pass,
                onValueChange = { pass = it },
                label = { Text("Contraseña (mínimo 6 caracteres)") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                isError = authError != null,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                        )
                    }
                }
            )
            Spacer(Modifier.height(16.dp))

            // Botón con estado de carga
            Button(
                onClick = {
                    vm.register(name, apellido, email, pass,
                        onSuccess = { onRegistered() },
                        onError = { }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                enabled = !isLoading && name.isNotBlank() && apellido.isNotBlank() &&
                        email.isNotBlank() && pass.length >= 6
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Creando cuenta...")
                } else {
                    Text("Registrarme", style = MaterialTheme.typography.labelLarge)
                }
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { onNavigate("login") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                enabled = !isLoading
            ) {
                Text("¿Ya tienes cuenta? Inicia sesión aquí")
            }
        }
    }
}