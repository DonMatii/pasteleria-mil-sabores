package com.grupo8.apppasteleriamilsabores.data.local

import androidx.room.*
import com.grupo8.apppasteleriamilsabores.data.model.Productos
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(productos: List<Productos>)

    @Query("SELECT * FROM productos")
    fun all(): Flow<List<Productos>>

    @Query("SELECT COUNT(*) FROM productos")
    suspend fun count(): Int


    @Query("SELECT * FROM productos WHERE destacado = 1")
    fun featured(): Flow<List<Productos>>
}
