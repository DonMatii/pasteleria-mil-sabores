package com.grupo8.apppasteleriamilsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo8.apppasteleriamilsabores.data.api.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Estados posibles para la carga del clima
sealed class WeatherState {
    object Loading : WeatherState()
    data class Success(val weather: com.grupo8.apppasteleriamilsabores.data.model.WeatherResponse) : WeatherState()
    data class Error(val message: String) : WeatherState()
}

class WeatherViewModel : ViewModel() {
    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    // Carga inicial del clima al crear el ViewModel
    init {
        loadWeather()
    }

    // Función para cargar el clima de Viña del Mar desde OpenWeatherMap
    fun loadWeather() {
        viewModelScope.launch {
            _weatherState.value = WeatherState.Loading
            try {
                val weather = ApiClient.weatherService.getWeather()
                _weatherState.value = WeatherState.Success(weather)
            } catch (e: Exception) {
                _weatherState.value = WeatherState.Error("No se pudo cargar el clima")
            }
        }
    }
}