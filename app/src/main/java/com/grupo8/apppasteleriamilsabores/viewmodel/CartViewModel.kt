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

    // Procesar compra - guardar en Firestore y limpiar carrito
    fun purchase(userEmail: String? = null) {
        viewModelScope.launch {
            try {
                // Crear orden para Firestore
                val order = FirestoreOrder(
                    orderId = UUID.randomUUID().toString(), // ID único generado automáticamente
                    userEmail = userEmail ?: "invitado", // Email del usuario o "invitado" para usuarios no logueados
                    items = ui.value.lines, // Lista de productos del carrito
                    total = ui.value.total, // Total calculado de la compra
                    timestamp = Timestamp.now(), // Fecha y hora actual de la compra
                    status = "completed" // Estado del pedido - completado
                )

                // Guardar orden en Firestore Database
                saveOrderToFirestore(order)

                // Limpiar carrito local después de guardar la orden
                repo.clearCart()

            } catch (e: Exception) {
                // Manejar error en caso de fallo al guardar - por ahora solo imprimir
                e.printStackTrace()
            }
        }
    }

    // Función para guardar orden en Firestore Database
    private suspend fun saveOrderToFirestore(order: FirestoreOrder) {
        val db = FirebaseFirestore.getInstance() // Obtener instancia de Firestore
        db.collection("orders") // Colección "orders" en Firestore
            .document(order.orderId) // Documento con ID único de la orden
            .set(order) // Guardar objeto orden completo
            .await() // Esperar a que se complete la operación de guardado
    }
}