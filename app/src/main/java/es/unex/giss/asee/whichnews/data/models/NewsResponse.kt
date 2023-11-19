package es.unex.giss.asee.whichnews.data.models
data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<News>
)