package app.pankaj.api.auth.domain

import app.pankaj.api.auth.domain.model.Register
import app.pankaj.dao.UserDao

interface UserRepository {

    suspend fun getUser(userId: Int): UserDao?
    suspend fun registerUser(register: Register): UserDao?
    suspend fun findUserByEmail(email: String): UserDao?
}