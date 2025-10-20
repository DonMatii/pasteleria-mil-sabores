package com.grupo8.apppasteleriamilsabores.data.local

import androidx.room.*
import com.grupo8.apppasteleriamilsabores.data.model.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: CartItem): Long

    @Query("SELECT * FROM cart_items")
    fun all(): Flow<List<CartItem>>

    @Query("DELETE FROM cart_items WHERE id = :id")
    suspend fun remove(id: Long)

    @Query("DELETE FROM cart_items")
    suspend fun clear()
}
