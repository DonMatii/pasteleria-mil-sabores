package com.grupo8.apppasteleriamilsabores.data.model

data class CartLineUi(
    val idProd: Long,
    val productId: Long,
    val nombreProd: String,
    val cantidadProd: Int,
    val precioProd: Double,
    val subtotal: Double
)
