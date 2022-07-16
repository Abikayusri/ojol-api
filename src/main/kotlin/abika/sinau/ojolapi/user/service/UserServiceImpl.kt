package abika.sinau.ojolapi.user.service

import abika.sinau.ojolapi.user.entity.User
import abika.sinau.ojolapi.user.entity.UserLogin
import abika.sinau.ojolapi.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl: UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun login(userLogin: UserLogin): Result<User> {
        return userRepository.getUserByUsername(userLogin.username)
    }

    override fun register(user: User): Result<Boolean> {
        return userRepository.insertUser(user)
    }

    override fun getUsers(): List<User> {
        return userRepository.getUsers()
    }
}