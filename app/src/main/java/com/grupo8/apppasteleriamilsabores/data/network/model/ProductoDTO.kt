// ProductoDTO.kt
package com.grupo8.apppasteleriamilsabores.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductoDTO(
    val idProd: Long,
    val categProd: String,
    val nombreProd: String,
    val descProd: String,
    val precioProd: Double,
    val imagenProd: String?,
    val destacado: Boolean
)