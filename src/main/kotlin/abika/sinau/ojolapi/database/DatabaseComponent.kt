package abika.sinau.ojolapi.database

import com.mongodb.client.MongoClient
import org.litote.kmongo.KMongo
import org.springframework.stereotype.Component

@Component
class DatabaseComponent {
    companion object {
        private const val DB_URL = "mongodb+srv://abikayusri:1234@cluster0.cqhuquv.mongodb.net/?retryWrites=true&w=majority"
    }
    
    val database: MongoClient = KMongo.createClient(DB_URL)
}