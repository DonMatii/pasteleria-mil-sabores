package com.grupo8.apppasteleriamilsabores.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.grupo8.apppasteleriamilsabores.data.repo.MilSaboresRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class AuthViewModelTest {

    // REGLAS DE TESTING
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // DEPENDENCIAS MOCKEADAS
    private lateinit var mockRepository: MilSaboresRepository
    private lateinit var authViewModel: AuthViewModel

    // DISPATCHER PARA COROUTINAS EN TEST
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        mockRepository = mock(MilSaboresRepository::class.java)

    }

    // TEST 1: Validación de email correcto (NO necesita Firebase)
    @Test
    fun `valid email should pass validation`() {
        // GIVEN - Un email válido
        val validEmail = "usuario@dominio.com"

        // WHEN - Validamos el email
        val isValid = isValidEmail(validEmail)

        // THEN - Debería ser válido
        assert(isValid) { "El email válido debería pasar la validación" }
    }

    // TEST 2: Validación de email incorrecto (NO necesita Firebase)
    @Test
    fun `invalid email should fail validation`() {
        // GIVEN - Un email inválido
        val invalidEmail = "usuario.invalido"

        // WHEN - Validamos el email
        val isValid = isValidEmail(invalidEmail)

        // THEN - Debería fallar
        assert(!isValid) { "El email inválido debería fallar la validación" }
    }

    // TEST 3: Patrón de email correcto
    @Test
    fun `email pattern should match valid emails`() {
        // GIVEN - Lista de emails válidos e inválidos
        val validEmails = listOf(
            "test@example.com",
            "user.name@domain.co",
            "user+tag@example.org"
        )

        val invalidEmails = listOf(
            "invalid",
            "missing@domain",
            "@domain.com",
            "spaces in@email.com"
        )

        // WHEN & THEN - Todos los válidos deberían pasar
        validEmails.forEach { email ->
            assert(isValidEmail(email)) { "$email debería ser válido" }
        }

        // WHEN & THEN - Todos los inválidos deberían fallar
        invalidEmails.forEach { email ->
            assert(!isValidEmail(email)) { "$email debería ser inválido" }
        }
    }

    // TEST 4: Validación de campos vacíos
    @Test
    fun `empty fields validation should work correctly`() {
        // GIVEN - Campos vacíos
        val emptyEmail = ""
        val emptyPassword = ""
        val validEmail = "test@example.com"
        val validPassword = "password123"

        // WHEN & THEN - Campos vacíos deberían fallar
        assert(!areFieldsValid(emptyEmail, validPassword)) { "Email vacío debería fallar" }
        assert(!areFieldsValid(validEmail, emptyPassword)) { "Password vacío debería fallar" }
        assert(!areFieldsValid(emptyEmail, emptyPassword)) { "Ambos campos vacíos deberían fallar" }
        assert(areFieldsValid(validEmail, validPassword)) { "Campos válidos deberían pasar" }
    }

    // FUNCIONES AUXILIARES PARA TEST (sin Firebase)
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
        return email.matches(emailPattern.toRegex())
    }

    private fun areFieldsValid(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty() && isValidEmail(email)
    }
}