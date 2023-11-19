import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.unex.giss.asee.whichnews.R
import es.unex.giss.asee.whichnews.api.ApiService
import es.unex.giss.asee.whichnews.data.models.News
import es.unex.giss.asee.whichnews.data.models.NewsResponse
import es.unex.giss.asee.whichnews.databinding.FragmentNewsBinding
import es.unex.giss.asee.whichnews.view.FilterActivity
import es.unex.giss.asee.whichnews.view.NewsAdapter
import es.unex.giss.asee.whichnews.view.api.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var ibFilter: ImageButton
    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Utilizamos View Binding para inflar el diseño
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = view.findViewById(R.id.recyclerViewNews)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        // Obtener datos y actualizar el RecyclerView
        fetchDataAndUpdateUI()

        // Establecer listeners
        setupListeners()

        return view
    }

    private fun setupListeners() {
        // Iniciar activity de filtro
        ibFilter = binding.ibFilter
        ibFilter.setOnClickListener {
            // Por ejemplo, iniciar la actividad FilterActivity
            val intent = Intent(activity, FilterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        fetchDataAndUpdateUI()
    }

    private fun fetchDataAndUpdateUI() {
        // Obtener las preferencias de filtro almacenadas
        val sharedPrefs = activity?.getSharedPreferences("Filters", Context.MODE_PRIVATE)
        val selectedCategories = sharedPrefs?.getStringSet("selectedCategories", emptySet()) ?: emptySet()

        val apiKey = "931cf5b1fe644332bd48555eb2a52432"
        val country = "us"
        val sources = ""
        val q = ""
        val pageSize = 10
        val page = 1

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
            val newsService = NewsService()
            val apiService: ApiService = newsService.getApiService()

            val call: Call<NewsResponse> = apiService.getNews(apiKey, country, news_category, sources, q, pageSize, page)

            call.enqueue(object : Callback<NewsResponse> {
                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                    if (response.isSuccessful) {
                        val newsResponse = response.body()
                        val newsList: List<News> = newsResponse?.articles ?: emptyList()

                    } else {
                        Log.e("NEWS_FRAGMENT", "Error: no se ha conseguido una respuesta de la API.")
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    Log.e("NEWS_FRAGMENT", "Error: no se podido conectar a la API.")
                }
            })
        }
    }
}
