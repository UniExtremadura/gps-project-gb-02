package es.unex.giss.asee.whichnews.view.menu

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giss.asee.whichnews.R
import es.unex.giss.asee.whichnews.data.models.News
import es.unex.giss.asee.whichnews.database.WhichNewsDatabase
import es.unex.giss.asee.whichnews.databinding.FragmentHistoryBinding
import es.unex.giss.asee.whichnews.utils.getHistory
import es.unex.giss.asee.whichnews.view.cardnews.NewsAdapter
import kotlinx.coroutines.launch
import java.lang.RuntimeException

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HistoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var db: WhichNewsDatabase
    private var _binding: FragmentHistoryBinding?=null
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
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

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
        setUpRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        setUpRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }

    private fun setUpRecyclerView(){

        lifecycleScope.launch {

            // Actualizar el RecyclerView con la lista combinada
            adapter = NewsAdapter(
                getHistory(requireContext()),
                onClickItem =
                {
                    listener.onNewsClick(it, R.id.historyFragment)
                },
                context = context
            )

            with(binding){
                rvHistoryList.layoutManager = LinearLayoutManager(context)
                rvHistoryList.adapter = adapter
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}