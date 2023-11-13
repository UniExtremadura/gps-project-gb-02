package es.unex.giss.asee.whichnews.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.unex.giss.asee.whichnews.data.models.NewsLike

@Dao
interface NewsLikeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(like: NewsLike)

    @Query("DELETE FROM news_like WHERE newsId = :newsId AND userId = :userId")
    suspend fun delete(newsId: String, userId: String)

    @Query("SELECT EXISTS (SELECT 1 FROM news_like WHERE newsId = :newsId AND userId = :userId)")
    suspend fun hasUserLiked(newsId: String, userId: String): Boolean

    @Query("SELECT COUNT(*) FROM news_like WHERE newsId = :newsId")
    suspend fun countLikes(newsId: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewsLike(newsLike: NewsLike)

    @Query("DELETE FROM news_like WHERE newsId = :newsId AND userId = :userId")
    fun deleteNewsLike(newsId: String, userId: Long)

    @Query("SELECT COUNT(*) FROM news_like WHERE newsId = :newsId AND userId = :userId")
    fun getLikeCountForAUser(newsId: String, userId: Long): Int
}
