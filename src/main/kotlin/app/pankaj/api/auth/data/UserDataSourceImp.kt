package app.pankaj.api.auth.data

import app.pankaj.dao.UserDao

class UserDataSourceImp : UserDataSource {
    override fun getUser(userId: Int): UserDao? {
        return UserDao.findById(userId)
    }

    override fun insertUser(userId: Int): UserDao? {
        TODO("Not yet implemented")
    }
}