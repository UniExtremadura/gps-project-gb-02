package es.unex.giss.asee.whichnews.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) var userId: Long?,
    val firstname: String = "",
    val secondname: String = "",
    val user: String = "",
    val email: String = "",
    val password: String = ""
): Serializable {

    constructor(user : String, password: String) : this(null, "", "", user, "", password){
    }

}