package es.unex.giss.asee.whichnews.view.api
import es.unex.giss.asee.whichnews.api.ApiService
import es.unex.giss.asee.whichnews.data.models.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// NewsService.kt
class NewsService {
    private val newsApiService: ApiService = createApiService()

    fun getFilteredNews(
        apiKey: String,
        country: String,
        category: String,
        sources: String,
        q: String,
        pageSize: Int,
        page: Int,
        callback: Callback<NewsResponse>
    ) {
        val call: Call<NewsResponse> = newsApiService.getNews(apiKey, country, category, sources, q, pageSize, page)
        call.enqueue(callback)
    }

    fun getApiService(): ApiService {
        return newsApiService
    }

    private fun createApiService(): ApiService {
        // Aquí puedes implementar la creación de tu ApiService
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
