package com.grupo8.apppasteleriamilsabores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.room.Room
import com.grupo8.apppasteleriamilsabores.data.local.MilSaboresDatabase
import com.grupo8.apppasteleriamilsabores.data.repo.MilSaboresRepository
import com.grupo8.apppasteleriamilsabores.ui.nav.MilSaboresNav
import com.grupo8.apppasteleriamilsabores.ui.theme.MilSaboresTheme
import com.grupo8.apppasteleriamilsabores.viewmodel.AuthViewModel
import com.grupo8.apppasteleriamilsabores.viewmodel.CartViewModel
import com.grupo8.apppasteleriamilsabores.viewmodel.StoreViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // DB Room
            val db = remember {
                Room.databaseBuilder(
                    applicationContext,
                    MilSaboresDatabase::class.java,
                    "milsabores.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }

            // Repo
            val repo = remember {
                MilSaboresRepository(
                    userDao = db.userDao(),
                    productDao = db.productDao(),
                    cartDao = db.cartDao()
                )
            }

            // ViewModels locales (sin Hilt)
            val authVm = remember { AuthViewModel(repo) }
            val storeVm = remember { StoreViewModel(repo) }
            val cartVm  = remember { CartViewModel(repo) }

            MilSaboresTheme {
                LaunchedEffect(Unit) { storeVm.seedIfEmpty() }

                MilSaboresNav(
                    authVm = authVm,
                    storeVm = storeVm,
                    cartVm = cartVm
                )
            }
        }
    }
}

