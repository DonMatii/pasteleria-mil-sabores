// ApiService.kt
package com.grupo8.apppasteleriamilsabores.data.network.api

import com.grupo8.apppasteleriamilsabores.data.network.model.ProductoDTO
import retrofit2.http.GET

interface ApiService {
    @GET("api/v1/productos/status")
    suspend fun getStatus(): String

    @GET("api/v1/productos/destacados")
    suspend fun getProductosDestacados(): List<ProductoDTO>

    @GET("api/v1/productos")
    suspend fun getAllProductos(): List<ProductoDTO>

    @GET("api/v1/productos/categorias")
    suspend fun getCategorias(): List<String>
}