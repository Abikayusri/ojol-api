package abika.sinau.ojolapi.user.entity

data class UserRequest(
        val username: String,
        val password: String
) {
    fun mapToNewUser(): User {
        return User.createNewUser(username, password)
    }
}