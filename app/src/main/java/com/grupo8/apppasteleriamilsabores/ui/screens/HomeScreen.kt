package com.grupo8.apppasteleriamilsabores.ui.screens

import android.content.Intent
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupo8.apppasteleriamilsabores.data.model.Productos
import com.grupo8.apppasteleriamilsabores.ui.components.MilBottomNav
import com.grupo8.apppasteleriamilsabores.ui.components.MilTopBar
import com.grupo8.apppasteleriamilsabores.ui.components.ProductCard
import com.grupo8.apppasteleriamilsabores.viewmodel.AuthViewModel
import com.grupo8.apppasteleriamilsabores.viewmodel.WeatherViewModel
import com.grupo8.apppasteleriamilsabores.viewmodel.WeatherState
import kotlinx.coroutines.delay

// WebView final sin warnings
@Composable
fun SpotifyEmbed() {
    var webViewFailed by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    if (webViewFailed) {
        FallbackSpotifyUI()
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        webViewClient = object : WebViewClient() {
                            override fun onPageFinished(view: WebView?, url: String?) {
                                isLoading = false
                            }

                            @Suppress("DEPRECATION", "OverridingDeprecatedMember")
                            override fun onReceivedError(
                                view: WebView?,
                                errorCode: Int,
                                description: String?,
                                failingUrl: String?
                            ) {
                                webViewFailed = true
                                isLoading = false
                                Log.d("WebView", "WebView error: $errorCode")
                            }
                        }
                        // JavaScript necesario para Spotify embed (seguro en este contexto)
                        @Suppress("SetJavaScriptEnabled")
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.mediaPlaybackRequiresUserGesture = false

                        loadUrl("https://open.spotify.com/embed/playlist/7HYoc4lo87o4JoBSOgdsBM?utm_source=generator")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            )

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

// UI de fallback elegante
@Composable
fun FallbackSpotifyUI() {
    val context = LocalContext.current

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "🎵 Playlist Musical",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    "Disfruta nuestra playlist en Spotify",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = {
                    openSpotifyPlaylist(context)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1DB954),
                    contentColor = Color.White
                ),
                modifier = Modifier.height(40.dp)
            ) {
                Text("Abrir")
            }
        }
    }
}

// Función optimizada sin warnings
private fun openSpotifyPlaylist(context: android.content.Context) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, "spotify:playlist:7HYoc4lo87o4JoBSOgdsBM".toUri())
        context.startActivity(intent)
    } catch (_: Exception) {
        // Fallback a versión web (ignoramos la excepción específica)
        val intent = Intent(Intent.ACTION_VIEW, "https://open.spotify.com/playlist/7HYoc4lo87o4JoBSOgdsBM".toUri())
        context.startActivity(intent)
    }
}

@Composable
fun HomeScreen(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onGoCatalog: () -> Unit,
    destacados: List<Productos>,
    authVm: AuthViewModel
) {
    val authState by authVm.authState.collectAsState()
    val isGuestUser = authState is AuthViewModel.AuthState.Authenticated &&
            (authState as AuthViewModel.AuthState.Authenticated).isGuest

    val isLoggedInUser = authState is AuthViewModel.AuthState.Authenticated &&
            !(authState as AuthViewModel.AuthState.Authenticated).isGuest

    val currentUserEmail = authVm.getCurrentUserEmail()

    var showWelcomeToast by remember { mutableStateOf(false) }
    var showUpgradePrompt by remember { mutableStateOf(false) }

    // ViewModel para el clima
    val weatherVm: WeatherViewModel = viewModel()
    val weatherState by weatherVm.weatherState.collectAsState()

    LaunchedEffect(isGuestUser) {
        if (isGuestUser) {
            showWelcomeToast = true
            delay(5000)
            showWelcomeToast = false

            delay(8000)
            showUpgradePrompt = true
        }
    }

    Scaffold(
        topBar = {
            MilTopBar(
                title = when {
                    isGuestUser -> "Pastelería Mil Sabores"
                    isLoggedInUser -> "Bienvenido/a"
                    else -> "Pastelería Mil Sabores"
                },
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
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                // Tarjeta del clima
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
                        Text(
                            "🌤️ Clima en Viña del Mar",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.height(8.dp))

                        when (weatherState) {
                            is WeatherState.Loading -> {
                                Text("Cargando clima...")
                            }
                            is WeatherState.Success -> {
                                val weather = (weatherState as WeatherState.Success).weather
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column {
                                        Text(
                                            "Temperatura: ${weather.main.temp}°C",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Text(
                                            "Condición: ${weather.weather.firstOrNull()?.description ?: "N/A"}",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                    Text(
                                        "📍 ${weather.name}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                            is WeatherState.Error -> {
                                Text(
                                    "No se pudo cargar el clima",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }

                // Tarjeta de bienvenida para usuarios logueados
                if (isLoggedInUser && currentUserEmail != null) {
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
                                        "¡Bienvenido a Pastelería Mil Sabores!",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        "Estás logueado como: $currentUserEmail",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                            Spacer(Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    authVm.logout()
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            ) {
                                Text("Cerrar Sesión")
                            }
                        }
                    }
                }

                // Tarjeta informativa para usuarios invitados
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

                // Sección de productos destacados
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
                                modifier = Modifier
                                    .width(240.dp)
                                    .clickable {
                                        onGoCatalog()
                                    }
                            ) {
                                Column(Modifier.padding(12.dp)) {
                                    ProductCard(p = p, onAddToCart = { }, showAddButton = false)
                                }
                            }
                        }
                    }

                    Text(
                        "💡 Toca cualquier producto para ir al catálogo y comprar",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Sección de playlist musical
                Text(
                    "🎵 Playlist del Mes",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(Modifier.height(8.dp))

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            "Música para Endulzar tu Día 🎶",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.height(12.dp))

                        SpotifyEmbed()

                        Spacer(Modifier.height(12.dp))

                        val context = LocalContext.current
                        Button(
                            onClick = {
                                openSpotifyPlaylist(context)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1DB954),
                                contentColor = Color.White
                            )
                        ) {
                            Text("🎵 Abrir en Spotify")
                        }

                        Spacer(Modifier.height(8.dp))
                        Text(
                            "También disponible en nuestra tienda física",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Botón para navegar al catálogo completo
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

                Spacer(Modifier.height(32.dp))
            }

            // Diálogo para promocionar registro
            if (showUpgradePrompt) {
                AlertDialog(
                    onDismissRequest = { showUpgradePrompt = false },
                    title = { Text("¡No te pierdas los beneficios!") },
                    text = {
                        Text("Regístrate ahora y obtén:\n\n" +
                                "Descuentos exclusivos\n" +
                                "Acumulación de puntos\n" +
                                "Ofertas especiales\n" +
                                "Historial de pedidos\n" +
                                "Notificaciones de nuevos productos")
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

            // Toast de bienvenida
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
                            containerColor = Color(0xFFFFE4E9),
                            contentColor = Color(0xFF8B4513)
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