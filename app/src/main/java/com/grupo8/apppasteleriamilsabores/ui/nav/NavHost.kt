package com.grupo8.apppasteleriamilsabores.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
    val nav = rememberNavController()
    val currentBackStackEntry = nav.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route ?: Route.Home.path

    val cartUi by cartVm.ui.collectAsState()
    val products by storeVm.products.collectAsState()
    val destacados by storeVm.featured.collectAsState()

    // NUEVO: Observar el estado de autenticación
    val authState by authVm.authState.collectAsState()

    // NUEVO: Controlar navegación basada en estado de autenticación
    LaunchedEffect(authState) {
        when (authState) {
            is AuthViewModel.AuthState.Loading -> {
                // Esperar mientras verifica
            }
            is AuthViewModel.AuthState.NotAuthenticated -> {
                // Si no está autenticado, ir a login (solo si no está ya en login)
                if (currentRoute != Route.Login.path && currentRoute != Route.Registro.path) {
                    nav.navigate(Route.Login.path) {
                        popUpTo(0) // Limpiar back stack
                    }
                }
            }
            is AuthViewModel.AuthState.Authenticated -> {
                val isGuest = (authState as AuthViewModel.AuthState.Authenticated).isGuest
                // Si está autenticado y está en login, ir a home
                if (currentRoute == Route.Login.path || currentRoute == Route.Registro.path) {
                    nav.navigate(Route.Home.path) {
                        popUpTo(0) // Limpiar back stack
                    }
                }
            }
        }
    }

    // CAMBIO IMPORTANTE: startDestination ahora es Login en lugar de Home
    NavHost(navController = nav, startDestination = Route.Login.path) {

        composable(Route.Login.path) {
            LoginScreen(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    if (route != currentRoute) nav.navigate(route)
                },
                vm = authVm,
                onLoggedIn = {
                    // Solo navegar si realmente se logueó
                    if (authVm.isLoggedIn.value) {
                        nav.navigate(Route.Home.path) {
                            popUpTo(0)
                        }
                    }
                }
            )
        }

        composable(Route.Registro.path) {
            RegisterScreen(
                currentRoute = currentRoute,
                onNavigate = { route -> if (route != currentRoute) nav.navigate(route) },
                vm = authVm,
                onRegistered = {
                    nav.popBackStack()
                    nav.navigate(Route.Login.path)
                }
            )
        }

        composable(Route.Home.path) {
            HomeScreen(
                currentRoute = currentRoute,
                onNavigate = { route -> if (route != currentRoute) nav.navigate(route) },
                onGoCatalog = { nav.navigate(Route.Catalogo.path) },
                destacados = destacados,
                authVm = authVm
            )
        }

        composable(Route.Catalogo.path) {
            CatalogScreen(
                currentRoute = currentRoute,
                onNavigate = { route -> if (route != currentRoute) nav.navigate(route) },
                products = products,
                onAddToCart = { id -> cartVm.add(id) }
            )
        }

        composable(Route.Carrito.path) {
            val ui = cartVm.ui.collectAsState().value
            CartScreen(
                currentRoute = currentRoute,
                onNavigate = { route -> if (route != currentRoute) nav.navigate(route) },
                lines = ui.lines,
                onRemove = { id -> cartVm.remove(id) },
                onClear = { cartVm.clear() }
            )
        }
    }
}