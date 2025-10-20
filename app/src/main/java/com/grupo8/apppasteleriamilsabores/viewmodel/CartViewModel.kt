package com.grupo8.apppasteleriamilsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo8.apppasteleriamilsabores.data.model.CartItem
import com.grupo8.apppasteleriamilsabores.data.repo.MilSaboresRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CartUi(val items: List<CartItem> = emptyList())

class CartViewModel(private val repo: MilSaboresRepository): ViewModel() {
    val ui: StateFlow<CartUi> = repo.cart().map { CartUi(it) }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), CartUi()
    )

    fun add(productId: Long, qty: Int = 1) {
        viewModelScope.launch { repo.addToCart(productId, qty) }
    }

    fun remove(id: Long) {
        viewModelScope.launch { repo.removeCartItem(id) }
    }

    fun clear() {
        viewModelScope.launch { repo.clearCart() }
    }
}
