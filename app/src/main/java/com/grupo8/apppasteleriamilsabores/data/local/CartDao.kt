package com.grupo8.apppasteleriamilsabores.data.local

import androidx.room.*
import com.grupo8.apppasteleriamilsabores.data.model.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItem): Long

    @Query("SELECT * FROM cart_items")
    fun all(): Flow<List<CartItem>>

    // 👉 Busca si ya hay una línea con ese producto
    @Query("SELECT * FROM cart_items WHERE productoId = :productId LIMIT 1")
    suspend fun findByProduct(productId: Long): CartItem?

    // 👉 Incrementa cantidad
    @Query("UPDATE cart_items SET cantidadProds = cantidadProds + :delta WHERE id = :id")
    suspend fun incQty(id: Long, delta: Int)

    @Query("DELETE FROM cart_items WHERE id = :id")
    suspend fun remove(id: Long)

    @Query("DELETE FROM cart_items")
    suspend fun clear()
}


