package app.pankaj.api.auth.domain

import app.pankaj.api.auth.data.UserDataSource
import app.pankaj.api.auth.domain.model.Register
import app.pankaj.dao.UserDao
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserRepositoryImp(
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun getUser(userId: Int): UserDao? {
        val userDao = newSuspendedTransaction {
            return@newSuspendedTransaction userDataSource.getUser(userId)
        }
        return userDao
    }

    override suspend fun registerUser(register: Register): UserDao? {
        TODO("Not yet implemented")
    }


}