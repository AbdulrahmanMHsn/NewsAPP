package amhsn.domain.repository

import amhsn.domain.entities.Article
import amhsn.domain.entities.NewsRequest
import amhsn.domain.entities.NewsResponse
import kotlinx.coroutines.flow.Flow

interface NewsRepo {

    suspend fun getNewsRemote(newsRequest: NewsRequest,page:Int):NewsResponse


    suspend fun insertNewsLocal(list: NewsResponse)


    suspend fun getNewsLocal(): Flow<NewsResponse>

}