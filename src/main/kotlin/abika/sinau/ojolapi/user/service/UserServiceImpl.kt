package abika.sinau.ojolapi.user.service

import abika.sinau.ojolapi.authentication.JWTConfig
import abika.sinau.ojolapi.exception.OjolException
import abika.sinau.ojolapi.user.entity.LoginResponse
import abika.sinau.ojolapi.user.entity.User
import abika.sinau.ojolapi.user.entity.UserLogin
import abika.sinau.ojolapi.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl: UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun login(userLogin: UserLogin): Result<LoginResponse> {
        val resultUser = userRepository.getUserByUsername(userLogin.username)

        return resultUser.map {
            val token = JWTConfig.generateToken(it)
            val passwordInDb = it.password
            val passwordRequest = userLogin.password
            if (passwordInDb == passwordRequest) {
                LoginResponse(token)
            } else {
                throw OjolException("Password Invalid!")
            }
        }
    }

    override fun register(user: User): Result<Boolean> {
        return userRepository.insertUser(user)
    }

    override fun getUsers(): Result<List<User>> {
        return userRepository.getUsers()
    }

    override fun getUserByUsername(username: String): Result<User> {
        return userRepository.getUserByUsername(username).map {
            it.password = null
            it
        }
    }

    override fun getUserById(id: String): Result<User> {
        return userRepository.getUserById(id).map {
            it.password = null
            it
        }
    }
}