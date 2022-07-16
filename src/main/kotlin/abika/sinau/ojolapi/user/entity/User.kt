package abika.sinau.ojolapi.user.entity

import com.fasterxml.jackson.annotation.JsonIgnore

data class User(
        @JsonIgnore
        var id: String = "",
        var username: String = "",
        var password: String = ""
)
