package amhsn.data.repository

import amhsn.data.mapper.toNewsRequestData
import amhsn.data.mapper.toNewsResponseDomain
import amhsn.data.remote.Ktor
import amhsn.data.remote.api_service.NewsAPI
import amhsn.domain.entities.NewsRequest
import amhsn.domain.entities.NewsResponse
import amhsn.domain.repository.NewsRepo

class NewsRepoImpl(private val newsApi:NewsAPI = NewsAPI(Ktor.client)):NewsRepo {

    override suspend fun getNews(newsRequest: NewsRequest,page:Int): NewsResponse {
        return newsApi.getNews(newsRequest.toNewsRequestData(),page).toNewsResponseDomain()
    }

}