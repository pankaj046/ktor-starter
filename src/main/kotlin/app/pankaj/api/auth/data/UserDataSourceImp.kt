package app.pankaj.api.auth.data

import app.pankaj.api.auth.domain.model.Register
import app.pankaj.api.auth.domain.model.Role
import app.pankaj.dao.UserDao
import app.pankaj.dao.UsersTable
import app.pankaj.utils.TokenUtils.generatePassword
import app.pankaj.utils.randomString
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
            UserDao.new {
                email = request.email
                fullName = request.fullName
                password = generatePassword(request.password, saltValue)
                salt = saltValue
                role = Role.USER
                isDeleted = false
                isActive = false
                createdOn = Instant.now()
                updatedOn = Instant.now()
            }
        }
    }

    override suspend fun findUserByEmail(email: String): UserDao? {
        return  newSuspendedTransaction {
            return@newSuspendedTransaction UserDao.find { UsersTable.email eq email }.firstOrNull()
        }
    }
}