    package com.grupo8.apppasteleriamilsabores.data.local

    import androidx.room.*
    import com.grupo8.apppasteleriamilsabores.data.model.User
    import kotlinx.coroutines.flow.Flow

    @Dao
    interface UserDao {
        @Insert(onConflict = OnConflictStrategy.ABORT)
        suspend fun insert(user: User): Long

        @Query("SELECT * FROM users WHERE emailUser = :email LIMIT 1")
        suspend fun findByEmail(email: String): User?

        @Query("SELECT * FROM users")
        fun all(): Flow<List<User>>
    }
