package abika.sinau.ojolapi.user.service

import abika.sinau.ojolapi.user.entity.LoginResponse
import abika.sinau.ojolapi.user.entity.User
import abika.sinau.ojolapi.user.entity.UserLogin

interface UserService {

    fun login(userLogin: UserLogin): Result<LoginResponse>

    fun register(user: User): Result<Boolean>

    fun getUsers(): Result<List<User>>

    fun getUserByUsername(username: String): Result<User>

    fun getUserById(id: String): Result<User>
}