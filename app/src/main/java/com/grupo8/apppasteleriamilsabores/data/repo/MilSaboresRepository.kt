package com.grupo8.apppasteleriamilsabores.data.repo

import com.grupo8.apppasteleriamilsabores.data.local.*
import com.grupo8.apppasteleriamilsabores.data.model.*
import kotlinx.coroutines.flow.Flow

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

    // Products
    suspend fun seedProducts(productos: List<Productos>) = productDao.upsertAll(productos)
    fun products(): Flow<List<Productos>> = productDao.all()
    fun featured(): Flow<List<Productos>> = productDao.featured()

    suspend fun productsCount(): Int = productDao.count()
    suspend fun seedIfEmpty(products: List<Productos>) {
        if (productDao.count() == 0) {
            productDao.upsertAll(products)
        }
    }

    // Cart
    fun cart(): Flow<List<CartItem>> = cartDao.all()
    suspend fun addToCart(productId: Long, qty: Int) = cartDao.upsert(CartItem(productoId = productId, cantidadProds = qty))
    suspend fun removeCartItem(id: Long) = cartDao.remove(id)
    suspend fun clearCart() = cartDao.clear()
}
