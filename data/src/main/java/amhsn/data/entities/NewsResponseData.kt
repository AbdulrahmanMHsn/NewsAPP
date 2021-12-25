package amhsn.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NewsResponseData(
    @SerialName("articles")
    val articleData: List<ArticleData>,
    @SerialName("status")
    val status: String,
    @SerialName("totalResults")
    val totalResults: Int
)