package com.grupo8.apppasteleriamilsabores.data.api

import com.grupo8.apppasteleriamilsabores.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String = "Viña del Mar,CL",
        @Query("appid") apiKey: String = "5ee73aa43d28d3319172ee3a0a303db6",
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "es"
    ): WeatherResponse
}