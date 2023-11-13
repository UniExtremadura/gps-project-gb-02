package es.unex.giss.asee.whichnews.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.unex.giss.asee.whichnews.data.models.Review

@Dao
interface ReviewDao {
    @Query("SELECT * FROM Review")
    suspend fun getAllReviews(): List<Review>

    @Insert
    suspend fun insertReview(review: Review)
}
