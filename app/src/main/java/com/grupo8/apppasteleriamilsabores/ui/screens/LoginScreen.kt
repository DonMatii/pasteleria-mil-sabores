package com.grupo8.apppasteleriamilsabores.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.grupo8.apppasteleriamilsabores.viewmodel.AuthViewModel
import com.grupo8.apppasteleriamilsabores.ui.components.MilBottomNav
import com.grupo8.apppasteleriamilsabores.ui.components.MilTopBar
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    vm: AuthViewModel,
    onLoggedIn: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var showGuestDialog by remember { mutableStateOf(false) }

    // Observar estados del ViewModel
    val authState by vm.authState.collectAsState()
    val authError by vm.authError.collectAsState()
    val isLoading by vm.isLoading.collectAsState()
    val isAlreadyGuest = authState is AuthViewModel.AuthState.Authenticated &&
            (authState as AuthViewModel.AuthState.Authenticated).isGuest

    // Animación de fade out para errores antiguos
    val showOldError by remember { mutableStateOf(false) }
    val toastAlpha by animateFloatAsState(
        targetValue = if (showOldError) 1f else 0f,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 300),
        label = "toastAlpha"
    )

    // Efecto para limpiar errores cuando se cambian los campos
    LaunchedEffect(email, pass) {
        if (authError != null) {
            vm.clearError()
        }
    }

    Scaffold(
        topBar = { MilTopBar(title = "Iniciar sesión") },
        bottomBar = { MilBottomNav(currentRoute, onNavigate) }
    ) { inner ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Título de bienvenida
                Text(
                    "Bienvenido a Pastelería Mil Sabores",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Mostrar error del ViewModel si existe
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
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    isError = authError != null
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = pass,
                    onValueChange = { pass = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    isError = authError != null
                )
                Spacer(Modifier.height(12.dp))

                // Botón de login con estado de carga
                Button(
                    onClick = {
                        vm.login(email, pass) {
                            onLoggedIn()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    enabled = !isLoading && email.isNotBlank() && pass.isNotBlank()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Iniciando sesión...")
                    } else {
                        Text("Iniciar Sesión")
                    }
                }

                Spacer(Modifier.height(8.dp))

                // Botón de invitado con validación
                if (isAlreadyGuest) {
                    // Si ya es invitado, mostrar mensaje informativo
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text("✅ Ya estás en modo invitado")
                        }
                    }
                } else {
                    // Botón de invitado normal
                    Button(
                        onClick = { showGuestDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        enabled = !isLoading
                    ) {
                        Text("Continuar como Invitado")
                    }
                }

                Spacer(Modifier.height(8.dp))

                // Botón de registro
                Button(
                    onClick = { onNavigate("registro") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    enabled = !isLoading
                ) {
                    Text("Crear cuenta")
                }
            }

            // Diálogo informativo para modo invitado
            if (showGuestDialog && !isAlreadyGuest) {
                AlertDialog(
                    onDismissRequest = { showGuestDialog = false },
                    title = { Text("Modo Invitado") },
                    text = {
                        Text("Podrás:\n\n" +
                                "✅ Explorar productos\n" +
                                "✅ Agregar al carrito\n" +
                                "✅ Realizar compras\n\n" +
                                "Te recomendamos registrarte para:\n\n" +
                                "🎁 Descuentos exclusivos\n" +
                                "📦 Historial de pedidos\n" +
                                "⭐ Acumulación de puntos")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                showGuestDialog = false
                                vm.loginAnonimo {
                                    onLoggedIn()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text("Continuar como Invitado")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                showGuestDialog = false
                                onNavigate("registro")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text("Registrarme")
                        }
                    }
                )
            }
        }
    }
}