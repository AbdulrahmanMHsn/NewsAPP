package amhsn.domain.entities


data class NewsRequest(
     val country: String = "",
     val category: String = "",
     val q: String = "",
     val pageSize:Int= 10,
     val page:Int= 1,
)