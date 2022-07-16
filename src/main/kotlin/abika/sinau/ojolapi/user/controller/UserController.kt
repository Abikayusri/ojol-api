package abika.sinau.ojolapi.user.controller

import abika.sinau.ojolapi.user.entity.LoginResponse
import abika.sinau.ojolapi.user.entity.User
import abika.sinau.ojolapi.user.entity.UserLogin
import abika.sinau.ojolapi.user.entity.UserRequest
import abika.sinau.ojolapi.user.service.UserService
import abika.sinau.ojolapi.utils.BaseResponse
import abika.sinau.ojolapi.utils.toResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ojol/user")
class UserController {

    @Autowired
    private lateinit var userService: UserService

//    @GetMapping
//    fun getUserByName(@RequestParam("username") userName: String): BaseResponse<User> {
//        return userService.getUserByUsername(userName).toResponses()
//    }

    @GetMapping
    fun getUser(): BaseResponse<User> {
        val userId = SecurityContextHolder.getContext().authentication.principal as? String
        return userService.getUserById(userId.orEmpty()).toResponses()
    }

    @PostMapping("/login")
    fun login(@RequestBody userLogin: UserLogin): BaseResponse<LoginResponse> {
        return userService.login(userLogin).toResponses()
    }

//    @PostMapping("/register")
//    fun register(@RequestBody user: User): BaseResponse<Boolean> {
//        return userService.register(user).toResponses()
//    }

    @PostMapping("/register")
    fun register(@RequestBody userRequest: UserRequest): BaseResponse<Boolean> {
        return userService.register(userRequest.mapToNewUser()).toResponses()
    }

//    @GetMapping
//    fun getUsers(): BaseResponse<List<User>> {
//        return userService.getUsers().toResponses()
//    }
}