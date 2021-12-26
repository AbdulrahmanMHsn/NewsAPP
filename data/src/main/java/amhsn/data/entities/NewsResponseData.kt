package amhsn.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "articles")
@Serializable
data class NewsResponseData(
    @PrimaryKey
    var id:Int = 1,
    @ColumnInfo(name = "articles")
    @SerialName("articles")
    var articleData: List<ArticleData> = listOf(),
    @Ignore
    @SerialName("status")
    val status: String="",
    @Ignore
    @SerialName("totalResults")
    val totalResults: Int=0
)