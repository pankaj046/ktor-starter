package app.pankaj.api.auth.data

import app.pankaj.dao.UserDao

interface UserDataSource {
    fun getUser(userId: Int): UserDao?
    fun insertUser(userId: Int): UserDao?
}