package com.grupo8.apppasteleriamilsabores.data.api

import com.grupo8.apppasteleriamilsabores.data.model.springboot.ContactMessageRequest
import com.grupo8.apppasteleriamilsabores.data.model.springboot.ContactMessageResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface SpringBootContactService {
    @POST("contacto")  // 👈 QUITA el "/api/" de aquí
    suspend fun enviarMensaje(@Body message: ContactMessageRequest): Response<ContactMessageResponse>
}

// ✅ NUEVO: Retrofit Client específico para contacto
object ContactRetrofitClient {
    private const val BASE_URL = "https://watchful-terresa-gnostically.ngrok-free.dev/api/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val contactService: SpringBootContactService by lazy {
        retrofit.create(SpringBootContactService::class.java)
    }
}