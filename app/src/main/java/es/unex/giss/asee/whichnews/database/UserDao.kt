package es.unex.giss.asee.whichnews.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.unex.giss.asee.whichnews.data.models.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE name LIKE :first LIMIT 1")
    suspend fun find(first: String): User

    @Insert
    suspend fun insert(user: User): Long
}