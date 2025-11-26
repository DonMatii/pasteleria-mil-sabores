package com.grupo8.apppasteleriamilsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo8.apppasteleriamilsabores.data.model.CartLineUi
import com.grupo8.apppasteleriamilsabores.data.model.FirestoreOrder
import com.grupo8.apppasteleriamilsabores.data.repo.MilSaboresRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

// Estado de la UI del carrito
data class CartUi(
    val lines: List<CartLineUi> = emptyList(),
    val total: Double = 0.0
)

class CartViewModel(private val repo: MilSaboresRepository): ViewModel() {

    // Estado actual del carrito para la UI
    val ui: StateFlow<CartUi> = repo.cartLines()
        .map { lines -> CartUi(lines = lines, total = lines.sumOf { it.subtotal }) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CartUi())

    // Agregar producto al carrito
    fun add(productId: Long, qty: Int = 1) {
        viewModelScope.launch { repo.addToCart(productId, qty) }
    }

    // Remover producto del carrito
    fun remove(id: Long) {
        viewModelScope.launch { repo.removeCartItem(id) }
    }

    // Vaciar todo el carrito
    fun clear() {
        viewModelScope.launch { repo.clearCart() }
    }

    // Actualizar cantidad de producto en el carrito
    fun updateQuantity(productId: Long, newQuantity: Int) {
        viewModelScope.launch {
            if (newQuantity <= 0) {
                // Si la cantidad es 0 o menor, eliminar producto
                repo.removeCartItem(productId)
            } else {
                // Actualizar cantidad manteniendo el producto
                repo.updateCartItemQuantity(productId, newQuantity)
            }
        }
    }

    // Procesar compra - guardar en Firestore y limpiar carrito
    fun purchase(userEmail: String? = null) {
        viewModelScope.launch {
            try {
                // Crear orden para Firestore
                val order = FirestoreOrder(
                    orderId = UUID.randomUUID().toString(),
                    userEmail = userEmail ?: "invitado",
                    items = ui.value.lines,
                    total = ui.value.total,
                    timestamp = Timestamp.now(),
                    status = "completed"
                )

                // Guardar orden en Firestore Database
                saveOrderToFirestore(order)

                // Limpiar carrito local después de guardar la orden
                repo.clearCart()

            } catch (e: Exception) {
                // Manejar error en caso de fallo al guardar
                e.printStackTrace()
            }
        }
    }

    // Función para guardar orden en Firestore Database
    private suspend fun saveOrderToFirestore(order: FirestoreOrder) {
        val db = FirebaseFirestore.getInstance()
        db.collection("orders")
            .document(order.orderId)
            .set(order)
            .await()
    }
}