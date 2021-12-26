package amhsn.domain.entities



data class NewsResponse(
    val articles: List<Article>,
    val status: String = "",
    val totalResults: Int = 0
)