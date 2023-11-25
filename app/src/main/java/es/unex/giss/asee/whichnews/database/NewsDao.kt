package es.unex.giss.asee.whichnews.database

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import es.unex.giss.asee.whichnews.data.models.News
import es.unex.giss.asee.whichnews.data.models.UserNewsCrossRef
import es.unex.giss.asee.whichnews.data.models.UserWithNews

@Dao
interface NewsDao {
    // --------------------------------------------------------

    @Query("SELECT * FROM News WHERE url = :url")
    suspend fun findNews(url: String): News

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(news: News): Long

    @Query("UPDATE News SET is_favourite = :isFavorite WHERE url = :url")
    suspend fun setLike(url: String?, isFavorite: Boolean)

    @Query("UPDATE News SET is_viewed = :isViewed WHERE url = :url")
    suspend fun setViewed(url: String?, isViewed: Boolean)

    @Delete
    suspend fun delete(news: News)

    @Transaction
    @Query("""
        SELECT News.* FROM News
        JOIN UserNewsCrossRef ON News.newsId = UserNewsCrossRef.newsId
        WHERE UserNewsCrossRef.userId = :userId AND News.is_favourite = 1
    """)
    suspend fun getFavouriteNewsOfUser(userId: Long): List<News>

    @Transaction
    @Query("""
        SELECT News.* FROM News
        JOIN UserNewsCrossRef ON News.newsId = UserNewsCrossRef.newsId
        WHERE UserNewsCrossRef.userId = :userId AND News.is_viewed = 1
    """)
    suspend fun getHistory(userId: Long): List<News>

    // Relacionar un usuario a una noticia
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserNews(crossRef: UserNewsCrossRef)

    @Transaction
    suspend fun insertAndRelate(news: News, userId: Long) {
        val foundNews = findNews(news.url ?: "")
        if (foundNews != null) {
            // La noticia ya existe, obtener el ID existente
            Log.e("NEWS_DAO", "News already exists. ID: $foundNews")
            foundNews.newsId?.let { UserNewsCrossRef(userId, it) }?.let { insertUserNews(it) }

        } else {
            // La noticia no existe, realizar la inserción
            val newNewsId = insert(news)
            Log.e("NEWS_DAO", "$newNewsId")
            insertUserNews(UserNewsCrossRef(userId, newNewsId))
        }

    }
}
