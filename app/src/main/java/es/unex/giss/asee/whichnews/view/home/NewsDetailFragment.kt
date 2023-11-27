package es.unex.giss.asee.whichnews.view.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import es.unex.giss.asee.whichnews.R
import es.unex.giss.asee.whichnews.data.models.News
import es.unex.giss.asee.whichnews.database.WhichNewsDatabase
import es.unex.giss.asee.whichnews.utils.markNewsAsViewed
import es.unex.giss.asee.whichnews.databinding.FragmentLikesBinding
import es.unex.giss.asee.whichnews.databinding.FragmentNewsDetailBinding
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NewsDetailFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private val args: NewsDetailFragmentArgs by navArgs()
    private var _binding: FragmentNewsDetailBinding?=null
    private val binding get() = _binding!!

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnNewsClickListener){
            listener = context
        } else {
            throw RuntimeException("$context must implement OnNewsClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Utilizamos View Binding para inflar el diseño
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val news = args.news

        // Guardamos la noticia como 'vista'.
        markNewsAsViewed(news)

        with(binding){
            // Establecer valores en los elementos de la vista utilizando View Binding
            tvTitle.text = news.title
            tvPublishedAt.text = news.publishedAt
            tvContent.text = news.content
            tvSource.text = "Author: ${news.author}. Source: ${news.sourceId}"

            // Configurar clic en el botón "Like" si es necesario
            btnLike.setOnClickListener {
                lifecycleScope.launch {
                    var db: WhichNewsDatabase = WhichNewsDatabase.getInstance(requireContext())

                    if(!news.isFavourite){
                        Toast.makeText(context, "Added to 'My Likes'", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "Removed from 'My Likes'", Toast.LENGTH_SHORT).show()
                    }

                    db.newsDao().setLike(news.url, !news.isFavourite)
                }
            }

            // Configurar clic en el botón "Share" si es necesario
            btnShare.setOnClickListener {
                // TODO: Implementa la lógica para manejar el clic en el botón "Share" aquí
            }

            // Configurar clic en el botón "Report"
            btnReport.setOnClickListener {
                // TODO: Implementa la lógica para manejar el clic en el botón "Report" aquí
            }

            // Configurar clic en el botón "Write Review"
            btnWriteReview.setOnClickListener {
                listener.onNewsClick(news, R.id.newsDetailFragment)
            }
        }

        Log.d("NewsDetailFragment","Showing news details")
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewsDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}