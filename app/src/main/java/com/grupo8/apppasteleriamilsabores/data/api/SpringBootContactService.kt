package com.grupo8.apppasteleriamilsabores.data.api

import com.grupo8.apppasteleriamilsabores.data.model.springboot.ContactMessageRequest
import com.grupo8.apppasteleriamilsabores.data.model.springboot.ContactMessageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SpringBootContactService {
    @POST("/api/contacto")
    suspend fun enviarMensaje(@Body message: ContactMessageRequest): Response<ContactMessageResponse>
}