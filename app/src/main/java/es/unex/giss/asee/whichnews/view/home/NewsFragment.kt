package es.unex.giss.asee.whichnews.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.unex.giss.asee.whichnews.R
import es.unex.giss.asee.whichnews.api.ApiService
import es.unex.giss.asee.whichnews.data.models.NewsResponse
import es.unex.giss.asee.whichnews.databinding.FragmentNewsBinding
import es.unex.giss.asee.whichnews.view.FilterActivity
import es.unex.giss.asee.whichnews.view.JoinActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        val view = binding.root

        setupListeners()

        // Inflate the layout for this fragment
        return view
    }

    override fun onResume() {
        super.onResume()

        getTopHeadline()
    }

    private fun setupListeners(){
        with(binding){
            btFilter.setOnClickListener{ navigateToFilterActivity() }
        }
    }

    private fun getTopHeadline(){

        val sharedPrefs = activity?.getSharedPreferences("Filters", Context.MODE_PRIVATE)
        val selectedCategories = sharedPrefs?.getStringSet("selectedCategories", emptySet())?.toMutableList() ?: mutableListOf()
        val categoriesParam = selectedCategories.joinToString(",")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val call: Call<NewsResponse> = apiService.getTopHeadlines(
            apiKey = "931cf5b1fe644332bd48555eb2a52432",
            country = "us",
            category = categoriesParam
        )

        // mostrar los resultados
        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val newsResponse: NewsResponse? = response.body()
                    newsResponse?.articles?.forEach { article ->
                        Log.d("NEWS_FRAGMENT", "Título: ${article.title}")
                    }
                } else {

                    Log.e("NEWS_FRAGMENT", "Response error. State code: ${response.code()}")
                    Log.e("NEWS_FRAGMENT", "Response Body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.e("NEWS_FRAGMENT", "Error on API call: ${t.message}")
            }
        })

    }

    private fun navigateToFilterActivity(){
        val intent = Intent(activity?.applicationContext, FilterActivity::class.java)
        startActivity(intent)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}