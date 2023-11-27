package es.unex.giss.asee.whichnews.view.home
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giss.asee.whichnews.BuildConfig
import es.unex.giss.asee.whichnews.R
import es.unex.giss.asee.whichnews.api.ApiService
import es.unex.giss.asee.whichnews.api.RetrofitClient
import es.unex.giss.asee.whichnews.data.models.News
import es.unex.giss.asee.whichnews.data.models.NewsResponse
import es.unex.giss.asee.whichnews.database.WhichNewsDatabase
import es.unex.giss.asee.whichnews.databinding.FragmentRecentBinding
import es.unex.giss.asee.whichnews.view.cardnews.NewsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RecentFragment : Fragment() {

    private lateinit var emptyMessageTextView: TextView

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var db: WhichNewsDatabase
    private var _binding: FragmentRecentBinding?=null
    private val binding get() = _binding!!
    private lateinit var adapter: NewsAdapter

    private lateinit var listener: OnNewsClickListener
    interface OnNewsClickListener {
        fun onNewsClick(news: News, fragmentId: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Utilizamos View Binding para inflar el diseño
        _binding = FragmentRecentBinding.inflate(inflater, container, false)

        // TextView para mostrar un mensaje cuando la lista está vacía
        emptyMessageTextView = binding.emptyMessageTextView

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = WhichNewsDatabase.getInstance(context)!!
        if(context is OnNewsClickListener){
            listener = context
        }else{
            throw RuntimeException(context.toString() + " must implement onNewsClickListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAndSetUpRecentFeed()
    }

    override fun onResume() {
        super.onResume()
        loadAndSetUpRecentFeed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }

    companion object {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }

    private fun loadAndSetUpRecentFeed() {
        // crear el cliente de la API
        val apiService: ApiService = RetrofitClient.apiService
        val call: Call<NewsResponse> = apiService.getNews(
            apiKey = BuildConfig.NEWS_API_KEY,
            country = "us",
            category = "general",
            page = 1,
            pageSize = 20,
        )

        call.enqueue(object : Callback<NewsResponse>
        {
            // Se ejecuta si recibimos respuesta
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                val result: Result<NewsResponse> = RetrofitClient.handleApiResponse(response)

                // Si se produce error en la llamada se imprime
                if (!result.isSuccess) {
                    val error = result.exceptionOrNull()
                    Log.e("RECENT_FRAGMENT", "Error: ${error?.message}")
                    return
                }

                Log.e("RECENT_FRAGMENT", "CONEXIÓN A LA API REALIZADA")
                val newsResponse = result.getOrNull()
                val newsList: List<News> = newsResponse?.articles ?: emptyList()

                // Obtener la fecha de hoy
                val today = Calendar.getInstance().time

                // Filtrar las noticias de la última semana
                val filteredNewsList = newsList.filter { news ->
                    val newsDate = dateFormat.parse(news.publishedAt)
                    isWithinLastFiveDays(newsDate, today)
                }
                // Log para verificar las fechas filtradas
                for (news in filteredNewsList) {
                    Log.e("RECENT_FRAGMENT", "Fecha de publicación: ${news.publishedAt}")
                }
                setUpRecyclerView(filteredNewsList)
            }

            // Se ejecuta si se produce un error de red
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.e("RECENT_FRAGMENT", "Error on API call: ${t.message}")
            }
        })
    }

    private fun setUpRecyclerView(newsList: List<News>){
        // Actualizar el RecyclerView con la lista filtrada
        adapter = NewsAdapter(
            newsList,
            onClickItem =
            {
                listener.onNewsClick(it, R.id.recentFragment)
            },
            context = context
        )

        with(binding){
            rvNewsList.layoutManager = LinearLayoutManager(context)
            rvNewsList.adapter = adapter
        }

        // Mostrar o ocultar el mensaje cuando la lista está vacía
        if (newsList.isEmpty()) {
            emptyMessageTextView.visibility = View.VISIBLE
        } else {
            emptyMessageTextView.visibility = View.GONE
        }
    }

    // Función para verificar si la noticia ha sido publicada en los últimos cinco días
    private fun isWithinLastFiveDays(newsDate: Date, currentDate: Date): Boolean {
        val calNews = Calendar.getInstance().apply { time = newsDate }
        val calCurrent = Calendar.getInstance().apply { time = currentDate }

        // Comparar las fechas para verificar si están dentro de la última semana
        return calNews.after(Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -5) }) &&
                calNews.before(calCurrent)
    }
}
