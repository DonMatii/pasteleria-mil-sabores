package com.grupo8.apppasteleriamilsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo8.apppasteleriamilsabores.data.model.Productos
import com.grupo8.apppasteleriamilsabores.data.repo.MilSaboresRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StoreViewModel(private val repo: MilSaboresRepository): ViewModel() {
    val products: StateFlow<List<Productos>> = repo.products().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val featured: StateFlow<List<Productos>> = repo.featured().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    fun seedIfEmpty() {
        viewModelScope.launch {
            if (repo.productsCount() == 0) {
                repo.seedProducts(
                    listOf(
                        Productos(
                            idProd = 0,
                            nombreProd = "Cheesecake frutilla",
                            descProd = "Base de galleta, crema y frutillas",
                            precioProd = 14990.0,
                            imagenProd = "cheesecake_trad",
                            categProd = "Postres",
                            destacado = true
                        ),
                        Productos(
                            idProd = 0,
                            nombreProd = "Brownie nuez",
                            descProd = "Brownie con trozos de nuez",
                            precioProd = 3990.0,
                            imagenProd = "brownie_choc",
                            categProd = "Dulces",
                            destacado = true
                        )
                    )
                )
            }
        }
    }

}
