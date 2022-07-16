package abika.sinau.ojolapi.user.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*

data class User(
        var id: String = "",
        var username: String = "",
        var password: String? = ""
) {
    companion object {
        fun createNewUser(username: String, password: String): User {
            return User(
                    id = UUID.randomUUID().toString(),
                    username = username,
                    password = password
            )
        }
    }
}
