// ApiService.kt
package com.grupo8.apppasteleriamilsabores.data.network.api

import com.grupo8.apppasteleriamilsabores.data.network.model.ProductoDTO
import com.grupo8.apppasteleriamilsabores.data.model.springboot.ContactMessageRequest
import com.grupo8.apppasteleriamilsabores.data.model.springboot.ContactMessageResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    // Endpoints de Productos (existentes)
    @GET("api/v1/productos/status")
    suspend fun getStatus(): String

    @GET("api/v1/productos/destacados")
    suspend fun getProductosDestacados(): List<ProductoDTO>

    @GET("api/v1/productos")
    suspend fun getAllProductos(): List<ProductoDTO>

    @GET("api/v1/productos/categorias")
    suspend fun getCategorias(): List<String>

    // Endpoint para Contacto
    @POST("contacto")
    suspend fun enviarMensajeContacto(@Body contactMessage: ContactMessageRequest): ContactMessageResponse
}