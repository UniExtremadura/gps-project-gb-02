package es.unex.giss.asee.whichnews.utils

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import es.unex.giss.asee.whichnews.data.models.News
import es.unex.giss.asee.whichnews.database.WhichNewsDatabase
import es.unex.giss.asee.whichnews.login.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun Fragment.markNewsAsViewed(news: News) {
    lifecycleScope.launch {
        val db = WhichNewsDatabase.getInstance(requireContext())
        val existingNews = db.newsDao().findNews(news.url ?: "")
        if (existingNews != null) {
            db.newsDao().setViewed(news.url, true)
        } else {
            // Aquí manejas la lógica de insertar la noticia y relacionarla si no existe
            news.isViewed = true
            UserManager.loadCurrentUser(requireContext())?.userId?.let { userId ->
                db.newsDao().insertAndRelate(news, userId)
            }
        }
    }
}

suspend fun Fragment.getHistory(context: Context, ): List<News>
{
    var historyList: List<News> = emptyList<News>()

    val db = WhichNewsDatabase.getInstance(context)
    UserManager.loadCurrentUser(context)?.userId?.let { userId ->
        historyList = db.newsDao().getHistory(userId)
    }

    return historyList
}
