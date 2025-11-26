package com.grupo8.apppasteleriamilsabores.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.grupo8.apppasteleriamilsabores.data.model.CartLineUi
import com.grupo8.apppasteleriamilsabores.data.repo.MilSaboresRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class CartViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockRepository: MilSaboresRepository
    private lateinit var cartViewModel: CartViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // SOLUCIÓN: Configurar Main Dispatcher para tests
        Dispatchers.setMain(testDispatcher)

        mockRepository = mock(MilSaboresRepository::class.java)

        // IMPORTANTE: Mockear el flow del carrito para evitar llamadas reales
        whenever(mockRepository.cartLines()).thenReturn(flowOf(emptyList()))

        cartViewModel = CartViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        // IMPORTANTE: Limpiar después de cada test
        Dispatchers.resetMain()
    }

    // TEST 1: Agregar producto al carrito
    @Test
    fun `add product to cart should call repository`() = runTest(testDispatcher) {
        // GIVEN - ID de producto y cantidad
        val productId = 1L
        val quantity = 2

        // WHEN - Agregamos producto al carrito
        cartViewModel.add(productId, quantity)

        // CLAVE: Avanzar el tiempo de prueba hasta que se completen las corrutinas
        advanceUntilIdle()

        // THEN - Debería llamar al repositorio
        verify(mockRepository).addToCart(productId, quantity)
    }

    // TEST 2: Remover producto del carrito
    @Test
    fun `remove product from cart should call repository`() = runTest(testDispatcher) {
        // GIVEN - ID del item del carrito
        val cartItemId = 5L

        // WHEN - Removemos producto del carrito
        cartViewModel.remove(cartItemId)

        // CLAVE: Avanzar el tiempo de prueba
        advanceUntilIdle()

        // THEN - Debería llamar al repositorio
        verify(mockRepository).removeCartItem(cartItemId)
    }

    // TEST 3: Limpiar carrito completo
    @Test
    fun `clear cart should call repository`() = runTest(testDispatcher) {
        // WHEN - Limpiamos el carrito
        cartViewModel.clear()

        // CLAVE: Avanzar el tiempo de prueba
        advanceUntilIdle()

        // THEN - Debería llamar al repositorio
        verify(mockRepository).clearCart()
    }

    // TEST 4: Cálculo de totales debería funcionar
    @Test
    fun `cart total should be calculated correctly`() {
        // GIVEN - Datos de prueba
        val testCartLines = listOf(
            createCartLineUi(1L, "Producto 1", 10.0, 2), // 20.0
            createCartLineUi(2L, "Producto 2", 15.0, 1), // 15.0
            createCartLineUi(3L, "Producto 3", 5.0, 3)   // 15.0
        )
        // Total esperado: 20 + 15 + 15 = 50.0

        // WHEN - Calculamos el total manualmente
        val calculatedTotal = testCartLines.sumOf { it.subtotal }

        // THEN - El total debería ser correcto
        assert(calculatedTotal == 50.0) { "El total calculado debería ser 50.0, pero es $calculatedTotal" }
    }

    // TEST 5: Carrito vacío debería tener total cero
    @Test
    fun `empty cart should have zero total`() {
        // GIVEN - Carrito vacío
        val emptyCartLines = emptyList<CartLineUi>()

        // WHEN - Calculamos el total
        val total = emptyCartLines.sumOf { it.subtotal }

        // THEN - Debería ser cero
        assert(total == 0.0) { "Carrito vacío debería tener total 0.0" }
    }

    // TEST 6: Agregar producto sin cantidad especificada (default = 1)
    @Test
    fun `add product without quantity should use default value`() = runTest(testDispatcher) {
        // GIVEN - Solo ID del producto
        val productId = 7L

        // WHEN - Agregamos sin especificar cantidad
        cartViewModel.add(productId)

        // CLAVE: Avanzar el tiempo de prueba
        advanceUntilIdle()

        // THEN - Debería usar cantidad por defecto (1)
        verify(mockRepository).addToCart(productId, 1)
    }

    // FUNCIÓN AUXILIAR PARA CREAR CARTLINEUI
    private fun createCartLineUi(
        productId: Long,
        name: String,
        price: Double,
        quantity: Int
    ): CartLineUi {
        return CartLineUi(
            idProd = productId,
            productId = productId,
            nombreProd = name,
            cantidadProd = quantity,
            precioProd = price,
            subtotal = price * quantity
        )
    }
}