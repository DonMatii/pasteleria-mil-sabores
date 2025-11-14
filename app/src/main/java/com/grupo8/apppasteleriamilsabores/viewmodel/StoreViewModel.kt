package com.grupo8.apppasteleriamilsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo8.apppasteleriamilsabores.data.model.Productos
import com.grupo8.apppasteleriamilsabores.data.repo.MilSaboresRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StoreViewModel(private val repo: MilSaboresRepository): ViewModel() {

    val products: StateFlow<List<Productos>> = repo.products()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val featured: StateFlow<List<Productos>> = repo.featured()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun seedIfEmpty() {
        viewModelScope.launch {
            if (repo.productsCount() == 0) {

            }
        }
    }
}

