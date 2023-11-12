package es.unex.giss.asee.whichnews.api

import es.unex.giss.asee.whichnews.data.models.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("country") country: String? = null,
        @Query("category") category: String? = null,
        @Query("sources") sources: String? = null,
        @Query("q") query: String? = null,
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String
    ): Call<NewsResponse>


}