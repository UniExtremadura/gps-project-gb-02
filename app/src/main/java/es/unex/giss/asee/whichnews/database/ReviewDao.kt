package es.unex.giss.asee.whichnews.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.unex.giss.asee.whichnews.data.models.Review

@Dao
interface ReviewDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReview(review: Review)

    @Query("SELECT * FROM Review WHERE userId = :userId AND newsId = :newsId")
    suspend fun getUserReviews(userId: Long, newsId: Long): List<Review>
}
