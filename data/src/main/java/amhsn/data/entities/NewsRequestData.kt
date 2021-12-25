package amhsn.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class NewsRequestData(
     val country: String = "",
//     val category: String = "",
//     val q: String = "",
//     val pageSize:Int= 10,
//     val page:Int= 1,
)