package abika.sinau.ojolapi.user.repository

import abika.sinau.ojolapi.user.entity.User

interface UserRepository {

    fun insertUser(user: User): Result<Boolean>

    fun getUserById(id: String): Result<User>

    fun getUserByUsername(username: String): Result<User>

    fun getUsers(): Result<List<User>>
}