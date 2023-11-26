package es.unex.giss.asee.whichnews.view.home
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giss.asee.whichnews.BuildConfig
import es.unex.giss.asee.whichnews.api.ApiService
import es.unex.giss.asee.whichnews.api.RetrofitClient
import es.unex.giss.asee.whichnews.data.models.News
import es.unex.giss.asee.whichnews.data.models.NewsResponse
import es.unex.giss.asee.whichnews.database.WhichNewsDatabase
import es.unex.giss.asee.whichnews.databinding.FragmentNewsBinding
import es.unex.giss.asee.whichnews.view.FilterActivity
import es.unex.giss.asee.whichnews.view.cardnews.NewsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Context
import es.unex.giss.asee.whichnews.R
import java.lang.RuntimeException

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NewsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var db: WhichNewsDatabase

    private var _binding: FragmentNewsBinding?=null
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
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        // Establecer listeners
        setupListeners()

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
        loadAndSetUpNewsFeed()
    }
    override fun onResume() {
        super.onResume()
        loadAndSetUpNewsFeed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }

    private fun setupListeners() {
        // Gestión de los filtros
        with(binding){
            ibFilter.setOnClickListener {
                // Por ejemplo, iniciar la actividad FilterActivity
                val intent = Intent(activity, FilterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun loadAndSetUpNewsFeed() {
        // Obtener las preferencias de filtro almacenadas
        val sharedPrefs = activity?.getSharedPreferences("Filters", Context.MODE_PRIVATE)
        val selectedCategories = sharedPrefs?.getStringSet("selectedCategories", emptySet()) ?: emptySet()

        // Convertir el conjunto de categorías seleccionadas a una lista para usar en la solicitud
        val selectedCategoriesList = selectedCategories?.toMutableList()
        // Si no hay categorías seleccionadas, agregar "general" a la lista
        if (selectedCategoriesList?.isEmpty() == true) {
            selectedCategoriesList.add("general")
        }

        // Lista para almacenar todas las noticias combinadas
        val combinedNewsList = mutableListOf<News>()

        // Iterar sobre cada categoría y realizar solicitudes separadas
        for (news_category in selectedCategoriesList.orEmpty()) {
            // crear el cliente de la API
            val apiService: ApiService = RetrofitClient.apiService
            val call: Call<NewsResponse> = apiService.getNews(
                apiKey = BuildConfig.NEWS_API_KEY,
                country = "us",
                category = news_category,
                page = 1,
                pageSize = 10,
            )

            call.enqueue(object : Callback<NewsResponse>
            {
                // Se ejecuta si recibimos respuesta
                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                    val result: Result<NewsResponse> = RetrofitClient.handleApiResponse(response)

                    // Si se produce error en la llamada se imprime
                    if (!result.isSuccess) {
                        val error = result.exceptionOrNull()
                        Log.e("NEWS_FRAGMENT", "Error: ${error?.message}")
                        return
                    }

                    Log.e("NEWS_FRAGMENT", "CONEXIÓN A LA API REALIZADA")

                    val newsResponse = result.getOrNull()
                    val newsList: List<News> = newsResponse?.articles ?: emptyList()

                    // Agregar las noticias de la categoría actual a la lista combinada
                    combinedNewsList.addAll(newsList)
                    // Mezclar la lista combinada de forma aleatoria
                    combinedNewsList.shuffle()
                    setUpRecyclerView(combinedNewsList)
                }

                // Se ejecuta si se produce un error de red
                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    Log.e("NEWS_FRAGMENT", "Error on API call: ${t.message}")
                }
            })
        }
    }

    private fun setUpRecyclerView(newsList: List<News>){
        // Actualizar el RecyclerView con la lista combinada
        adapter = NewsAdapter(
            newsList,
            onClickItem =
            {
                listener.onNewsClick(it, R.id.newsFragment)
            },
            context = context
        )

        with(binding){
            rvNewsList.layoutManager = LinearLayoutManager(context)
            rvNewsList.adapter = adapter
        }
    }
}
