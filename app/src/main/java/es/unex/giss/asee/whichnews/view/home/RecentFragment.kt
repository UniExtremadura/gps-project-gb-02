package es.unex.giss.asee.whichnews.view.home
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.unex.giss.asee.whichnews.R
import es.unex.giss.asee.whichnews.api.ApiService
import es.unex.giss.asee.whichnews.api.RetrofitClient
import es.unex.giss.asee.whichnews.data.models.News
import es.unex.giss.asee.whichnews.data.models.NewsResponse
import es.unex.giss.asee.whichnews.databinding.FragmentRecentBinding
import es.unex.giss.asee.whichnews.view.NewsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class RecentFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var emptyMessageTextView: TextView
    private lateinit var binding: FragmentRecentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Utilizamos View Binding para inflar el diseño
        binding = FragmentRecentBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = view.findViewById(R.id.recyclerViewNews)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        // TextView para mostrar un mensaje cuando la lista está vacía
        emptyMessageTextView = view.findViewById(R.id.emptyMessageTextView)

        // Obtener datos y actualizar el RecyclerView
        fetchDataAndUpdateUI()

        return view
    }

    override fun onResume() {
        super.onResume()
        fetchDataAndUpdateUI()
    }


    companion object {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }

    private fun fetchDataAndUpdateUI() {
        val apiKey = "931cf5b1fe644332bd48555eb2a52432"
        val country = "us"
        val category = "general"
        val sources = ""
        val q = ""
        val pageSize = 20
        val page = 1

        // crear el cliente de la API
        val apiService: ApiService = RetrofitClient.apiService
        val call: Call<NewsResponse> = apiService.getNews(apiKey, country, category, sources, q, pageSize, page)

        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                val result: Result<NewsResponse> = RetrofitClient.handleApiResponse(response)
                if (result.isSuccess) {
                    val newsResponse = result.getOrNull()
                    val newsList: List<News> = newsResponse?.articles ?: emptyList()

                    // Obtener la fecha de hoy
                    val today = Calendar.getInstance().time

                    // Filtrar las noticias de la última semana
                    val filteredNewsList = newsList.filter { news ->
                        val newsDate = dateFormat.parse(news.publishedAt)
                        isWithinLastWeek(newsDate, today)
                    }
                    // Log para verificar las fechas filtradas
                    for (news in filteredNewsList) {
                        Log.e("RECENT_FRAGMENT", "Fecha de publicación: ${news.publishedAt}")
                    }
                    // Actualizar el RecyclerView con la lista filtrada
                    adapter = NewsAdapter(requireContext(), filteredNewsList)
                    recyclerView.adapter = adapter

                    // Mostrar o ocultar el mensaje cuando la lista está vacía
                    if (filteredNewsList.isEmpty()) {
                        emptyMessageTextView.visibility = View.VISIBLE
                    } else {
                        emptyMessageTextView.visibility = View.GONE
                    }
                } else {
                    Log.e("RECENT_FRAGMENT", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.e("RECENT_FRAGMENT", "Error on API call: ${t.message}")
            }
        })
    }

    // Función para verificar si la noticia ha sido publicada en los últimos cinco días
    private fun isWithinLastWeek(newsDate: Date, currentDate: Date): Boolean {
        val calNews = Calendar.getInstance().apply { time = newsDate }
        val calCurrent = Calendar.getInstance().apply { time = currentDate }

        // Comparar las fechas para verificar si están dentro de la última semana
        return calNews.after(Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -5) }) &&
                calNews.before(calCurrent)
    }
}
