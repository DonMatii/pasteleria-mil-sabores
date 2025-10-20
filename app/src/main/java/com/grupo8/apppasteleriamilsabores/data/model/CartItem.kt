package com.grupo8.apppasteleriamilsabores.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "cart_items",
    foreignKeys = [
        ForeignKey(
            entity = Productos::class,
            parentColumns = ["idProd"],
            childColumns = ["productoId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CartItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productoId: Long,
    val cantidadProds: Int
)
