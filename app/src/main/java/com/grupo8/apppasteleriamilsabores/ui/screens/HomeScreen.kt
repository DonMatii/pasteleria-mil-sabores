package com.grupo8.apppasteleriamilsabores.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grupo8.apppasteleriamilsabores.data.model.Productos
import com.grupo8.apppasteleriamilsabores.ui.components.MilBottomNav
import com.grupo8.apppasteleriamilsabores.ui.components.MilTopBar
import com.grupo8.apppasteleriamilsabores.ui.components.ProductCard
import com.grupo8.apppasteleriamilsabores.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onGoCatalog: () -> Unit,
    destacados: List<Productos>,
    authVm: AuthViewModel
) {
    // Usar el estado del ViewModel
    val authState by authVm.authState.collectAsState()
    val isGuestUser = authState is AuthViewModel.AuthState.Authenticated &&
            (authState as AuthViewModel.AuthState.Authenticated).isGuest

    var showWelcomeToast by remember { mutableStateOf(false) }
    var showUpgradePrompt by remember { mutableStateOf(false) }

    LaunchedEffect(isGuestUser) {
        if (isGuestUser) {
            showWelcomeToast = true
            delay(5000)
            showWelcomeToast = false

            // Mostrar invitación a registrarse después de 8 segundos
            delay(8000)
            showUpgradePrompt = true
        }
    }

    Scaffold(
        topBar = {
            MilTopBar(
                title = if (isGuestUser) "Pastelería Mil Sabores" else "Pastelería Mil Sabores", // QUITADO el icono 🎭
                onCart = {
                    onNavigate("carrito")
                }
            )
        },
        bottomBar = {
            MilBottomNav(currentRoute, onNavigate)
        }
    ) { inner ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                if (isGuestUser) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        "Modo Invitado Activado",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        "Podrás comprar pero te perderás beneficios exclusivos",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                            Spacer(Modifier.height(8.dp))
                            // CORREGIDO: Botón "Salir" arriba del de registro
                            Button(
                                onClick = { authVm.logoutGuest() },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            ) {
                                Text("Salir")
                            }
                            Spacer(Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    showUpgradePrompt = true
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            ) {
                                Text("¡Regístrate para Descuentos!")
                            }
                        }
                    }
                }

                Text(
                    "Productos destacados del mes",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(Modifier.height(8.dp))

                if (destacados.isEmpty()) {
                    Text(
                        "Aún no hay destacados. Revisa el catálogo completo.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(destacados) { p ->
                            Card(
                                shape = MaterialTheme.shapes.large,
                                modifier = Modifier.width(240.dp)
                            ) {
                                Column(Modifier.padding(12.dp)) {
                                    ProductCard(p = p, onAddToCart = { })
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = onGoCatalog,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Ver catálogo completo")
                }
            }

            // Diálogo para invitar a registrarse
            if (showUpgradePrompt) {
                AlertDialog(
                    onDismissRequest = { showUpgradePrompt = false },
                    title = { Text("¡No te pierdas los beneficios!") },
                    text = {
                        Text("Regístrate ahora y obtén:\n\n" +
                                "✅ Descuentos exclusivos\n" +
                                "✅ Acumulación de puntos\n" +
                                "✅ Ofertas especiales\n" +
                                "✅ Historial de pedidos\n" +
                                "✅ Notificaciones de nuevos productos")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                showUpgradePrompt = false
                                authVm.logoutGuest()
                                onNavigate("registro")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text("Registrarme")
                        }
                    },
                    dismissButton = {
                        // CORREGIDO: Botón "Quizás después" con color
                        Button(
                            onClick = { showUpgradePrompt = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text("Quizás después")
                        }
                    }
                )
            }

            if (showWelcomeToast) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 80.dp)
                ) {
                    Card(
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(
                            containerColor = androidx.compose.ui.graphics.Color(0xFFFFE4E9),
                            contentColor = androidx.compose.ui.graphics.Color(0xFF8B4513)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("🍰")
                            Spacer(Modifier.width(8.dp))
                            Text("¡Bienvenido! Estás navegando como invitado")
                        }
                    }
                }
            }
        }
    }
}