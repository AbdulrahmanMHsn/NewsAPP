package amhsn.domain.repository

import amhsn.domain.entities.Article
import amhsn.domain.entities.NewsRequest
import amhsn.domain.entities.NewsResponse
import kotlinx.coroutines.flow.Flow

interface SearchRepo {
    suspend fun search(newsRequest: NewsRequest,page:Int):Result<List<Article>>
}