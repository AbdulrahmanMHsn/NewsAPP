package amhsn.domain.repository

import amhsn.domain.entities.Article
import amhsn.domain.entities.NewsRequest
import amhsn.domain.entities.NewsResponse

interface NewsRepo {

    suspend fun getNews(newsRequest: NewsRequest,page:Int):NewsResponse

}