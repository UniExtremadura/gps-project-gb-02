package es.unex.giss.asee.whichnews.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.unex.giss.asee.whichnews.data.models.News
import es.unex.giss.asee.whichnews.data.models.Saved

@Dao
interface SavedDao {
    @Insert
    suspend fun addFavorite(usuarioNoticiaFavorita: Saved)

    @Query("SELECT * FROM news INNER JOIN user_news_favorite ON news.newsId = user_news_favorite.newsId WHERE user_news_favorite.userId = :userId")
    suspend fun getFavoritesNews(userId: Int): List<News>
}