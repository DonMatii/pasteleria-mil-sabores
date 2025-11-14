package com.grupo8.apppasteleriamilsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class MainViewModel : ViewModel() {

    private val _mensaje = mutableStateOf("Bienvenido a Mil Sabores 🍰")
    val mensaje: State<String> = _mensaje

    fun cambiarMensaje(nuevo: String) {
        _mensaje.value = nuevo
    }
}
