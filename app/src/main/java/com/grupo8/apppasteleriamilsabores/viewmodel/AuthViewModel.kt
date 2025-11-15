package com.grupo8.apppasteleriamilsabores.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo8.apppasteleriamilsabores.data.repo.MilSaboresRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.regex.Pattern

class AuthViewModel(private val repo: MilSaboresRepository) : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    private val _showGuestWelcome = MutableStateFlow(false)
    val showGuestWelcome = _showGuestWelcome.asStateFlow()

    // Estados para manejar errores y carga
    private val _authError = MutableStateFlow<String?>(null)
    val authError = _authError.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // Estado de autenticación para controlar el flujo
    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState = _authState.asStateFlow()

    // Estados posibles
    sealed class AuthState {
        object Loading : AuthState()
        object NotAuthenticated : AuthState()
        data class Authenticated(val isGuest: Boolean) : AuthState()
    }

    // NUEVO: Patrón para validar email
    private val EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
    )

    init {
        checkAuthStateWithReset()
    }

    private fun checkAuthStateWithReset() {
        val currentUser = auth.currentUser

        if (currentUser?.isAnonymous == true) {
            auth.signOut()
            _authState.value = AuthState.NotAuthenticated
            _isLoggedIn.value = false
        } else {
            _authState.value = when {
                currentUser == null -> AuthState.NotAuthenticated
                currentUser.isAnonymous -> AuthState.Authenticated(isGuest = true)
                else -> AuthState.Authenticated(isGuest = false)
            }
            _isLoggedIn.value = currentUser != null
        }
        _authError.value = null
    }

    // NUEVO: Función para validar email
    private fun isValidEmail(email: String): Boolean {
        return EMAIL_PATTERN.matcher(email).matches()
    }

    // NUEVO: Función para validar campos antes de proceder
    private fun validateLoginFields(email: String, password: String): String? {
        return when {
            email.isBlank() || password.isBlank() -> "Por favor ingresa email y contraseña"
            !isValidEmail(email) -> "Por favor ingresa un email válido (ejemplo: usuario@dominio.com)"
            password.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
            else -> null
        }
    }

    private fun validateRegisterFields(name: String, apellido: String, email: String, password: String): String? {
        return when {
            name.isBlank() || apellido.isBlank() || email.isBlank() || password.isBlank() ->
                "Por favor completa todos los campos"
            !isValidEmail(email) -> "Por favor ingresa un email válido (ejemplo: usuario@dominio.com)"
            password.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
            else -> null
        }
    }

    fun checkAuthState() {
        val currentUser = auth.currentUser
        _authState.value = when {
            currentUser == null -> AuthState.NotAuthenticated
            currentUser.isAnonymous -> AuthState.Authenticated(isGuest = true)
            else -> AuthState.Authenticated(isGuest = false)
        }
        _isLoggedIn.value = currentUser != null
    }

    // Login mejorado con validaciones
    fun login(email: String, password: String, onSuccess: () -> Unit = {}) {
        // Validar campos primero
        val validationError = validateLoginFields(email, password)
        if (validationError != null) {
            _authError.value = validationError
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null

            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()

                if (result.user != null) {
                    _isLoggedIn.value = true
                    _authState.value = AuthState.Authenticated(isGuest = false)
                    _authError.value = null
                    onSuccess()
                }
            } catch (e: Exception) {
                _authError.value = when (e) {
                    is FirebaseAuthInvalidUserException -> "No existe una cuenta con este email"
                    is FirebaseAuthInvalidCredentialsException -> "Contraseña incorrecta"
                    else -> "Error al iniciar sesión: ${e.message}"
                }
                _isLoggedIn.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Registro mejorado con validaciones
    fun register(nombre: String, apellido: String, email: String, password: String, onSuccess: () -> Unit = {}, onError: (String) -> Unit = {}) {
        // Validar campos primero
        val validationError = validateRegisterFields(nombre, apellido, email, password)
        if (validationError != null) {
            _authError.value = validationError
            onError(validationError)
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null

            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()

                if (result.user != null) {
                    // Guardar datos adicionales en Room después del registro exitoso en Firebase
                    repo.register(nombre, apellido, email, password)
                    _isLoggedIn.value = true
                    _authState.value = AuthState.Authenticated(isGuest = false)
                    _authError.value = null
                    onSuccess()
                }
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is FirebaseAuthUserCollisionException -> "Ya existe una cuenta con este email"
                    is FirebaseAuthInvalidCredentialsException -> "El formato del email no es válido"
                    else -> "Error al registrar: ${e.message}"
                }
                _authError.value = errorMessage
                onError(errorMessage)
                _isLoggedIn.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loginAnonimo(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                auth.signOut()
                val result = auth.signInAnonymously().await()
                _isLoggedIn.value = (result.user != null)
                _showGuestWelcome.value = true
                _authState.value = AuthState.Authenticated(isGuest = true)
                _authError.value = null
                onSuccess()
            } catch (e: Exception) {
                _authError.value = "Error al entrar como invitado: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun hideGuestWelcome() {
        _showGuestWelcome.value = false
    }

    fun logoutGuest() {
        auth.signOut()
        _isLoggedIn.value = false
        _showGuestWelcome.value = false
        _authState.value = AuthState.NotAuthenticated
        _authError.value = null
    }

    fun logout() {
        auth.signOut()
        _isLoggedIn.value = false
        _showGuestWelcome.value = false
        _authState.value = AuthState.NotAuthenticated
        _authError.value = null
    }

    // Limpiar errores
    fun clearError() {
        _authError.value = null
    }

    fun getCurrentUserType(): String {
        return when {
            auth.currentUser == null -> "No logueado"
            auth.currentUser?.isAnonymous == true -> "Invitado"
            else -> "Registrado"
        }
    }

    // Obtener email del usuario actual
    fun getCurrentUserEmail(): String? {
        return auth.currentUser?.email
    }
}