package com.grupo8.apppasteleriamilsabores.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "productos")
data class Productos(
    @PrimaryKey(autoGenerate = true) val idProd: Long = 0,
    val categProd: String = "",
    val nombreProd: String,
    val descProd: String = "",
    val precioProd: Double,
    val imagenProd: String? = null,
    val destacado: Boolean = false
)
