package amhsn.data.remote.api_service

import amhsn.data.entities.NewsRequestData
import amhsn.data.entities.NewsResponseData
import io.ktor.client.*
import io.ktor.client.request.*

class NewsAPI(private val ktorClient: HttpClient) {

    suspend fun getNews(newsRequestData: NewsRequestData,page:Int) = ktorClient.get<NewsResponseData> {
        url("top-headlines")
        parameter("country", newsRequestData.country)
//        parameter("category", newsRequestData.category)
//        parameter("q", newsRequestData.q)
//        parameter("pageSize", 15)
//        parameter("page",page)
    }

}