package es.unex.giss.asee.whichnews.view.home
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.unex.giss.asee.whichnews.R
// import es.unex.giss.asee.whichnews.data.AppDatabase
import es.unex.giss.asee.whichnews.databinding.FragmentNewsBinding
import es.unex.giss.asee.whichnews.databinding.FragmentTrendingBinding
import es.unex.giss.asee.whichnews.view.NewsAdapter

class TrendingFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var binding: FragmentTrendingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Utilizamos View Binding para inflar el diseño
        binding = FragmentTrendingBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = view.findViewById(R.id.recyclerViewNews)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        // Obtener datos y actualizar el RecyclerView
        fetchDataAndUpdateUI()

        return view
    }

    private fun fetchDataAndUpdateUI() {
        // Obtener datos de la base de datos local
        //val newsLikeList = getNewsLikeDataFromDatabase()

        // Puedes manipular los datos según tus necesidades aquí antes de mostrarlos en el RecyclerView

        // Actualizar el RecyclerView con la lista combinada
        // adapter = NewsAdapter(requireContext(), manipulatedNewsList)
        //recyclerView.adapter = adapter
    }

    /*private fun getNewsLikeDataFromDatabase(): List<NewsLike> {
        // Acceder a la base de datos local y obtener la lista de NewsLike
        // val dao = AppDatabase.getInstance(requireContext()).newsLikeDao()
        // return dao.getAllNewsLikes()
    }*/
}
