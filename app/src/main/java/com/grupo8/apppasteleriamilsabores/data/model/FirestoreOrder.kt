package com.grupo8.apppasteleriamilsabores.data.model

import com.google.firebase.Timestamp

/**
 * Modelo para órdenes de compra en Firestore
 * Almacena información completa de pedidos en la nube
 */
data class FirestoreOrder(
    // ID único generado automáticamente
    val orderId: String = "",

    // Email del usuario que realizó la compra
    val userEmail: String = "",

    // Lista de productos comprados
    val items: List<CartLineUi> = emptyList(),

    // Total de la compra
    val total: Double = 0.0,

    // Fecha y hora de la compra (timestamp de Firestore)
    val timestamp: Timestamp = Timestamp.now(),

    // Estado del pedido
    val status: String = "completed"
)