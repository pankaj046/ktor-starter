package app.pankaj.api.auth.data

import app.pankaj.api.auth.domain.model.Register
import app.pankaj.dao.UserDao

interface UserDataSource {
    suspend fun getUser(userId: Int): UserDao?
    suspend fun insertUser(userId: Int): UserDao?
    suspend fun registerUser(request: Register): UserDao?
    suspend fun findUserByEmail(email: String): UserDao?
}