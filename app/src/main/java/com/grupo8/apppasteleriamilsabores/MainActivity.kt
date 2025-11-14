package com.grupo8.apppasteleriamilsabores

import android.os.Bundle
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.room.Room
import com.grupo8.apppasteleriamilsabores.R
import com.grupo8.apppasteleriamilsabores.data.model.Productos
import com.grupo8.apppasteleriamilsabores.data.local.MilSaboresDatabase
import com.grupo8.apppasteleriamilsabores.data.repo.MilSaboresRepository
import com.grupo8.apppasteleriamilsabores.ui.nav.MilSaboresNav
import com.grupo8.apppasteleriamilsabores.ui.theme.MilSaboresTheme
import com.grupo8.apppasteleriamilsabores.viewmodel.AuthViewModel
import com.grupo8.apppasteleriamilsabores.viewmodel.CartViewModel
import com.grupo8.apppasteleriamilsabores.viewmodel.StoreViewModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

suspend fun seedFromJsonIfEmpty(ctx: Context, repo: MilSaboresRepository) {
    if (repo.productsCount() > 0) return
    // Lectura archivo .json
    val json = ctx.resources.openRawResource(R.raw.productos).bufferedReader().use { it.readText() }
    val items: List<Productos> = Json { ignoreUnknownKeys = true }
        .decodeFromString<List<Productos>>(json)
        .map { it.copy(idProd = 0) }
    repo.seedProducts(items)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val db = remember {
                Room.databaseBuilder(
                    applicationContext,
                    MilSaboresDatabase::class.java,
                    "milsabores.db"
                )
                    .fallbackToDestructiveMigration() // en desarrollo
                    .build()
            }

            val repo = remember {
                MilSaboresRepository(
                    userDao = db.userDao(),
                    productDao = db.productDao(),
                    cartDao = db.cartDao()
                )
            }

            val authVm = remember { AuthViewModel(repo) }
            val storeVm = remember { StoreViewModel(repo) }
            val cartVm  = remember { CartViewModel(repo) }

            MilSaboresTheme {
                LaunchedEffect(Unit) {
                    //repo.clearAllProducts() -> borrado de items duplicados en catalogo
                    seedFromJsonIfEmpty(applicationContext, repo)
                }

                MilSaboresNav(
                    authVm = authVm,
                    storeVm = storeVm,
                    cartVm = cartVm
                )
            }
        }
    }
}


