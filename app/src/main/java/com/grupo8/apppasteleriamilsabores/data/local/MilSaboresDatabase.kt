package com.grupo8.apppasteleriamilsabores.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.grupo8.apppasteleriamilsabores.data.model.*

@Database(
    entities = [User::class, Productos::class, CartItem::class],
    version = 1,
    exportSchema = true
)
abstract class MilSaboresDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
}
