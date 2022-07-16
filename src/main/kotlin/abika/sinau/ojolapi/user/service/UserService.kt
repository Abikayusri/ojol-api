package abika.sinau.ojolapi.user.service

import abika.sinau.ojolapi.user.entity.User
import abika.sinau.ojolapi.user.entity.UserLogin

interface UserService {

    fun login(userLogin: UserLogin): Result<User>

    fun register(user: User): Result<Boolean>

    fun getUsers(): List<User>
}