package es.unex.giss.asee.whichnews.view.cardnews

import android.content.Context
import android.widget.Toast
import es.unex.giss.asee.whichnews.data.models.News
import es.unex.giss.asee.whichnews.database.WhichNewsDatabase
import es.unex.giss.asee.whichnews.login.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardNewsManager(private val context: Context) {

    var db: WhichNewsDatabase = WhichNewsDatabase.getInstance(context)
    fun onClickLike(news: News) {
        CoroutineScope(Dispatchers.Main).launch {
            val foundNews = db.newsDao().findNews(news.url ?: "")
            if(foundNews != null){ // si la encuentro
                if (foundNews.isFavourite) unsetFavorite(foundNews) else setFavorite(foundNews)
            }else{
                if (news.isFavourite) unsetFavorite(news) else setFavorite(news)
            }
        }
    }

    private fun setFavorite(news: News){
        CoroutineScope(Dispatchers.Main).launch{
            if(db.newsDao().findNews(news.url ?: "") != null){ // si existe la noticia
                db.newsDao().setLike(news.url, true)
            }else{ // si no existe,
                news.isFavourite = true
                UserManager.loadCurrentUser(context)?.userId?.let { userId ->
                    db.newsDao().insertAndRelate(news, userId)
                }
            }

            Toast.makeText(context, "Added to 'My Likes'", Toast.LENGTH_SHORT).show()
        }
    }

    private fun unsetFavorite(news: News){
        CoroutineScope(Dispatchers.Main).launch{
            db.newsDao().setLike(news.url, false)
            Toast.makeText(context, "Removed from 'My Likes'", Toast.LENGTH_SHORT).show()
        }
    }
}
