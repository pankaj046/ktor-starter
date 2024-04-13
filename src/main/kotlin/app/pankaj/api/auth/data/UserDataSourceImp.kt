package app.pankaj.api.auth.data

import app.pankaj.dao.UserDao
import app.pankaj.dao.UsersTable
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserDataSourceImp : UserDataSource {
    override suspend fun getUser(userId: Int): UserDao? {
        return UserDao.findById(userId)
    }

    override suspend fun insertUser(userId: Int): UserDao? {
        TODO("Not yet implemented")
    }

    override suspend fun findUserByEmail(email: String): UserDao? {
        return  newSuspendedTransaction {
            return@newSuspendedTransaction UserDao.find { UsersTable.email eq email }.firstOrNull()
        }
    }
}