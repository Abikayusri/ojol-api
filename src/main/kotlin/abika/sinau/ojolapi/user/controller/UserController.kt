package abika.sinau.ojolapi.user.controller

import abika.sinau.ojolapi.utils.BaseResponse
import abika.sinau.ojolapi.utils.toResponses
import abika.sinau.ojolapi.user.entity.User
import abika.sinau.ojolapi.user.entity.UserLogin
import abika.sinau.ojolapi.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ojol")
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @PostMapping("/login")
    fun login(@RequestBody userLogin: UserLogin): BaseResponse<User> {
        return userService.login(userLogin).toResponses()
    }

    @PostMapping("/register")
    fun register(@RequestBody user: User): BaseResponse<Boolean> {
        return userService.register(user).toResponses()
    }

//    @GetMapping
//    fun getUsers(): BaseResponse<List<User>> {
//        return userService.getUsers()
//    }
}