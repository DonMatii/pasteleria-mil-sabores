package com.grupo8.apppasteleriamilsabores.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.grupo8.apppasteleriamilsabores.ui.screens.*
import com.grupo8.apppasteleriamilsabores.viewmodel.*

@Composable
fun MilSaboresNav(
    authVm: AuthViewModel,
    storeVm: StoreViewModel,
    cartVm: CartViewModel
) {
    // Controlador de navegación principal - maneja el stack de pantallas
    val nav = rememberNavController()
    val currentBackStackEntry = nav.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route ?: "home"

    // Estados de datos para la UI - observables desde los ViewModels
    val cartUi by cartVm.ui.collectAsState()
    val products by storeVm.products.collectAsState()
    val destacados by storeVm.featured.collectAsState()

    // Estado de autenticación desde ViewModel - controla acceso a pantallas
    val authState by authVm.authState.collectAsState()

    // Control de navegación basado en autenticación - efecto que se ejecuta cuando cambia authState
    LaunchedEffect(authState) {
        when (authState) {
            is AuthViewModel.AuthState.Loading -> {
                // Esperar mientras verifica credenciales con Firebase Auth
            }
            is AuthViewModel.AuthState.NotAuthenticated -> {
                // Si no está autenticado, redirigir a login para control de acceso
                if (currentRoute != "login" && currentRoute != "registro") {
                    nav.navigate("login") {
                        popUpTo(0) // Limpiar historial de navegación completo
                    }
                }
            }
            is AuthViewModel.AuthState.Authenticated -> {
                // Si está autenticado y está en login, redirigir a home automáticamente
                if (currentRoute == "login" || currentRoute == "registro") {
                    nav.navigate("home") {
                        popUpTo(0) // Limpiar historial de navegación completo
                    }
                }
            }
        }
    }

    // Configuración del sistema de navegación con NavHost
    // Punto de inicio: Pantalla de Login para control de acceso inicial
    NavHost(navController = nav, startDestination = "login") {

        // Pantalla de inicio de sesión - Control de acceso a la aplicación
        composable(Route.Login.path) {
            LoginScreen(
                currentRoute = currentRoute,
                onNavigate = { route -> nav.navigate(route) },
                vm = authVm,
                onLoggedIn = { nav.navigate(Route.Home.path) { popUpTo(0) } }
            )
        }

        // Pantalla de registro de nuevos usuarios - Creación de cuentas
        composable(Route.Registro.path) {
            RegisterScreen(
                currentRoute = currentRoute,
                onNavigate = { route -> nav.navigate(route) },
                vm = authVm,
                onRegistered = {
                    nav.popBackStack() // Volver atrás en el stack
                    nav.navigate(Route.Login.path) // Navegar a login después del registro
                }
            )
        }

        // Pantalla principal - Home con productos destacados y bienvenida
        composable(Route.Home.path) {
            HomeScreen(
                currentRoute = currentRoute,
                onNavigate = { route -> nav.navigate(route) },
                onGoCatalog = { nav.navigate(Route.Catalogo.path) },
                destacados = destacados,
                authVm = authVm
            )
        }

        // Catálogo completo de productos - Grid con todos los productos disponibles
        composable(Route.Catalogo.path) {
            CatalogScreen(
                currentRoute = currentRoute,
                onNavigate = { route -> nav.navigate(route) },
                products = products,
                onAddToCart = { id -> cartVm.add(id) }
            )
        }

        // Carrito de compras - Gestión de productos seleccionados y proceso de compra
        composable(Route.Carrito.path) {
            // Obtener email del usuario actual para asociarlo a la orden
            val currentUserEmail = authVm.getCurrentUserEmail()

            CartScreen(
                currentRoute = currentRoute,
                onNavigate = { route -> nav.navigate(route) },
                lines = cartUi.lines,
                onRemove = { id -> cartVm.remove(id) },
                onClear = { cartVm.clear() },
                onPurchase = {
                    // Procesar compra - guardar en Firestore y mostrar mensaje
                    cartVm.purchase(currentUserEmail) // Pasar email para la orden en Firestore
                }
            )
        }

        // Pantalla de contacto - Formulario para consultas y sugerencias de clientes
        composable(Route.Contacto.path) {
            ContactScreen(
                currentRoute = currentRoute,
                onNavigate = { route -> nav.navigate(route) }
            )
        }
    }
}