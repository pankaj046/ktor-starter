package app.pankaj.api.auth.data

import app.pankaj.api.auth.domain.model.Register
import app.pankaj.api.auth.domain.model.Role
import app.pankaj.dao.UserDao
import app.pankaj.dao.UsersTable
import app.pankaj.utils.TokenUtils.generatePassword
import app.pankaj.utils.randomString
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.Instant

class UserDataSourceImp : UserDataSource {
    override suspend fun getUser(userId: Int): UserDao? {
        return UserDao.findById(userId)
    }

    override suspend fun insertUser(userId: Int): UserDao? {
        TODO("Not yet implemented")
    }

    override suspend fun registerUser(request: Register): UserDao? {
        val saltValue = randomString(24)
        return newSuspendedTransaction {
            val id = UsersTable.insertAndGetId {
                it[email] = request.email
                it[fullName] = request.fullName
                it[password] = generatePassword(request.password, saltValue)
                it[salt] = saltValue
                it[role] = Role.USER
                it[isDeleted] = false
                it[isActive] = false
                it[createdOn] = Instant.now()
                it[updatedOn] = Instant.now()
            }
            return@newSuspendedTransaction UserDao.findById(id)
        }
    }

    override suspend fun findUserByEmail(email: String): UserDao? {
        return  newSuspendedTransaction {
            return@newSuspendedTransaction UserDao.find { UsersTable.email eq email }.firstOrNull()
        }
    }
}