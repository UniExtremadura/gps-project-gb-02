package es.unex.giss.asee.whichnews.view.home
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.unex.giss.asee.whichnews.data.models.News
import es.unex.giss.asee.whichnews.database.WhichNewsDatabase
// import es.unex.giss.asee.whichnews.data.AppDatabase
import es.unex.giss.asee.whichnews.databinding.FragmentTrendingBinding
import es.unex.giss.asee.whichnews.view.cardnews.NewsAdapter
import java.lang.RuntimeException

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TrendingFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var db: WhichNewsDatabase

    private var _binding: FragmentTrendingBinding?=null
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
        _binding = FragmentTrendingBinding.inflate(inflater, container, false)

        fetchDataAndUpdateUI()

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
        // loadAndSetUpNewsFeed()
    }

    override fun onResume() {
        super.onResume()
        //loadAndSetUpNewsFeed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
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
