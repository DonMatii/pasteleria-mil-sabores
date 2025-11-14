package com.grupo8.apppasteleriamilsabores.data.repo

import com.grupo8.apppasteleriamilsabores.data.local.*
import com.grupo8.apppasteleriamilsabores.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class MilSaboresRepository(
    private val userDao: UserDao,
    private val productDao: ProductDao,
    private val cartDao: CartDao
) {
    // Users
    suspend fun register(name: String, apellido: String, email: String, password: String): Long {
        return userDao.insert(User(userName = name, apUser = apellido, emailUser = email, passwordUser = password))
    }
    suspend fun findUserByEmail(email: String) = userDao.findByEmail(email)
    fun users(): Flow<List<User>> = userDao.all()

    // Productos
    fun products(): Flow<List<Productos>> = productDao.all()
    fun featured(): Flow<List<Productos>> = productDao.featured()
    suspend fun productsCount(): Int = productDao.count()
    suspend fun seedProducts(list: List<Productos>) = productDao.upsertAll(list)
    suspend fun clearAllProducts(): Int = productDao.clearAll()

    // Carrito
    suspend fun addToCart(productId: Long, qty: Int) {
        val existing = cartDao.findByProduct(productId)
        if (existing != null) {
            cartDao.incQty(existing.id, qty)
        } else {
            cartDao.insert(CartItem(productoId = productId, cantidadProds = qty))
        }
    }

    suspend fun removeCartItem(id: Long) = cartDao.remove(id)
    suspend fun clearCart() = cartDao.clear()

    // Funcion de detalles de producto en carrito
    fun cartLines(): Flow<List<CartLineUi>> =
        combine(cartDao.all(), productDao.all()) { cartItems, products ->
            val productsById = products.associateBy { it.idProd }
            cartItems.map { ci ->
                val p = productsById[ci.productoId]
                val nombre = p?.nombreProd ?: "Producto #${ci.productoId}"
                val precio = p?.precioProd ?: 0.0
                CartLineUi(
                    idProd = ci.id,
                    productId = ci.productoId,
                    nombreProd = nombre,
                    cantidadProd = ci.cantidadProds,
                    precioProd = precio,
                    subtotal = precio * ci.cantidadProds
                )
            }
        }
}


