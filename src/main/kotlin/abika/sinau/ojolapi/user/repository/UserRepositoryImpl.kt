package abika.sinau.ojolapi.user.repository

import abika.sinau.ojolapi.database.DatabaseComponent
import abika.sinau.ojolapi.utils.toResult
import abika.sinau.ojolapi.user.entity.User
import com.mongodb.client.MongoCollection
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl: UserRepository {

    @Autowired
    private lateinit var databaseComponent: DatabaseComponent

    private fun userCollection(): MongoCollection<User> = databaseComponent.database.getDatabase("ojol").getCollection()

    override fun insertUser(user: User): Result<Boolean> {
        val existingUser = getUserByUsername(user.username)

        return if(existingUser.isSuccess) {
            throw IllegalStateException("User ${user.username} is exist")
        } else {
            userCollection().insertOne(user).wasAcknowledged().toResult()
        }
    }

    override fun getUserById(id: String): Result<User> {
        return userCollection().findOne(User::id eq id).toResult()
    }

    override fun getUserByUsername(username: String): Result<User> {
        return userCollection().findOne(User::username eq username).toResult("User $username not found")
    }

    override fun getUsers(): List<User> {
        return userCollection().find().toList()
    }
}