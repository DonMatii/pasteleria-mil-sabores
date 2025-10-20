package com.grupo8.apppasteleriamilsabores.ui.nav

import androidx.compose.runtime.Composable
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
    val nav = rememberNavController()

    // Productos del Store (para catálogo/destacados)
    val products by storeVm.products.collectAsState()

    // Ruta actual para que el BottomNav sepa qué ítem resaltar
    val currentBackStackEntry = nav.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route ?: Route.Home.path

    NavHost(navController = nav, startDestination = Route.Home.path) {

        composable(Route.Home.path) {
            HomeScreen(
                currentRoute = currentRoute,
                onNavigate   = { route -> if (route != currentRoute) nav.navigate(route) },
                onGoCatalog  = { nav.navigate(Route.Catalogo.path) }
            )
        }

        composable(Route.Catalogo.path) {
            CatalogScreen(
                currentRoute = currentRoute,
                onNavigate   = { route -> if (route != currentRoute) nav.navigate(route) },
                products     = products,
                onAddToCart  = { productId -> cartVm.add(productId) }
            )
        }

        composable(Route.Carrito.path) {
            val ui = cartVm.ui.collectAsState().value
            CartScreen(
                currentRoute = currentRoute,
                onNavigate   = { route -> if (route != currentRoute) nav.navigate(route) },
                items        = ui.items,
                onRemove     = { id -> cartVm.remove(id) },
                onClear      = { cartVm.clear() }
            )
        }

        composable(Route.Login.path) {
            LoginScreen(
                currentRoute = currentRoute,
                onNavigate   = { route -> if (route != currentRoute) nav.navigate(route) },
                vm           = authVm,
                onLoggedIn   = { nav.navigate(Route.Home.path) }
            )
        }

        composable("registro") {
            RegisterScreen(
                currentRoute = currentRoute,
                onNavigate = { route -> if (route != currentRoute) nav.navigate(route) },
                vm = authVm,
                onRegistered = {
                    // tras registro, vuelve a login o directo a home
                    nav.popBackStack()    // opcional
                    nav.navigate("login")
                }
            )
        }
    }
}

