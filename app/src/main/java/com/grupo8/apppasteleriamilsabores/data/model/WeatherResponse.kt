package com.grupo8.apppasteleriamilsabores.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>,
    val name: String
)

@Serializable
data class Main(
    val temp: Double
)

@Serializable
data class Weather(
    val main: String,
    val description: String
)