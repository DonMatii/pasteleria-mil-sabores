package com.grupo8.apppasteleriamilsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo8.apppasteleriamilsabores.data.repo.MilSaboresRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repo: MilSaboresRepository): ViewModel() {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    fun login(email: String, passwordHash: String) {
        viewModelScope.launch {
            val u = repo.findUserByEmail(email)
            _isLoggedIn.value = (u?.passwordUser == passwordHash)
        }
    }

    fun register(nombre: String, apellido: String ,email: String, password: String, onDone: () -> Unit = {}) {
        viewModelScope.launch {
            // si guardas hash, aplica aquí (por ahora plano)
            repo.register(nombre, apellido, email, password)
            onDone()
        }
    }


    fun logout() { _isLoggedIn.value = false }
}
