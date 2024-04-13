package app.pankaj.dao

import app.pankaj.api.auth.domain.model.Role
import app.pankaj.api.auth.domain.model.User
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.timestamp

object UsersTable: IntIdTable("Users") {
    val email = varchar("email", 256).uniqueIndex()
    val password = varchar("password", 256).nullable()
    val salt = varchar("salt", 24)
    val fullName = varchar("fullName", 64)
    val role = enumeration<Role>("role")
    val isDeleted = bool("isDeleted").default(false)
    val isActive = bool("isActive").default(false)
    val createdOn = timestamp("createdOn")
    val updatedOn = timestamp("updatedOn")
}

class UserDao(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<UserDao>(UsersTable)

    var email by UsersTable.email
    var password by UsersTable.password
    var salt by UsersTable.salt
    var fullName by UsersTable.fullName
    var role by UsersTable.role
    var isDeleted by UsersTable.isDeleted
    var isActive by UsersTable.isActive
    var createdOn by UsersTable.createdOn
    var updatedOn by UsersTable.updatedOn
}

fun UserDao.asUser() = User(
        id = id.value,
        email = email,
        fullName = fullName,
        role = role
    )

fun Iterable<UserDao>.asUsers() = this.map { it.asUser() }