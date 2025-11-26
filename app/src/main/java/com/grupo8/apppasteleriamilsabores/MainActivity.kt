package com.grupo8.apppasteleriamilsabores

import android.os.Bundle
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.room.Room
import com.grupo8.apppasteleriamilsabores.data.local.MilSaboresDatabase
import com.grupo8.apppasteleriamilsabores.data.model.Productos
import com.grupo8.apppasteleriamilsabores.data.repo.MilSaboresRepository
import com.grupo8.apppasteleriamilsabores.ui.nav.MilSaboresNav
import com.grupo8.apppasteleriamilsabores.ui.theme.MilSaboresTheme
import com.grupo8.apppasteleriamilsabores.viewmodel.AuthViewModel
import com.grupo8.apppasteleriamilsabores.viewmodel.CartViewModel
import com.grupo8.apppasteleriamilsabores.viewmodel.StoreViewModel
import kotlinx.serialization.json.Json
import com.grupo8.apppasteleriamilsabores.R


// CONFIGURACIÓN JSON GLOBAL - EVITA CREAR INSTANCIAS REDUNDANTES
private val jsonParser = Json { ignoreUnknownKeys = true }

/**
 * FUNCIÓN PARA CARGAR DATOS INICIALES DESDE JSON
 * Se ejecuta solo si la base de datos está vacía
 */
suspend fun seedFromJsonIfEmpty(ctx: Context, repo: MilSaboresRepository) {
    // Verificar si ya hay productos para evitar duplicados
    if (repo.productsCount() > 0) return

    // LECTURA DEL ARCHIVO JSON DESDE RECURSOS RAW
    val json = ctx.resources.openRawResource(R.raw.productos).bufferedReader().use { it.readText() }
    // DESERIALIZACIÓN Y MAPEO DE DATOS
    val items: List<Productos> = jsonParser
        .decodeFromString<List<Productos>>(json)
        .map { it.copy(idProd = 0) } // Resetear IDs para auto-generación

    // INSERTAR PRODUCTOS EN LA BASE DE DATOS
    repo.seedProducts(items)
}

/**
 * ACTIVIDAD PRINCIPAL DE LA APLICACIÓN
 * Configura toda la arquitectura de la app
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // CONFIGURACIÓN DE LA BASE DE DATOS ROOM
            val db = remember {
                Room.databaseBuilder(
                    applicationContext,
                    MilSaboresDatabase::class.java,
                    "milsabores.db"
                )
                    .fallbackToDestructiveMigration() // SOLO PARA DESARROLLO - ELIMINA EN PRODUCCIÓN
                    .build()
            }

            // REPOSITORIO PRINCIPAL - CAPA DE ACCESO A DATOS
            val repo = remember {
                MilSaboresRepository(
                    userDao = db.userDao(),
                    productDao = db.productDao(),
                    cartDao = db.cartDao()
                )
            }

            // CONFIGURACIÓN DE VIEWMODELS - CAPA DE LÓGICA DE NEGOCIO
            val authVm = remember { AuthViewModel(repo) }   // Maneja autenticación
            val storeVm = remember { StoreViewModel(repo) } // Maneja catálogo de productos
            val cartVm  = remember { CartViewModel(repo) }  // Maneja carrito de compras

            // TEMA PRINCIPAL DE LA APLICACIÓN
            MilSaboresTheme {
                // 🔄 EFECTO PARA CARGA INICIAL DE DATOS
                LaunchedEffect(Unit) {
                    // 💡 NOTA: Esta línea está comentada - solo usar para limpiar datos en desarrollo
                    // repo.clearAllProducts() -> borrado de items duplicados en catalogo

                    // CARGAR PRODUCTOS DESDE JSON SI LA BD ESTÁ VACÍA
                    seedFromJsonIfEmpty(applicationContext, repo)
                }

                // SISTEMA DE NAVEGACIÓN PRINCIPAL
                MilSaboresNav(
                    authVm = authVm,  // Navegación con control de autenticación
                    storeVm = storeVm,// Navegación de tienda
                    cartVm = cartVm   // Navegación de carrito
                )
            }
        }
    }
}