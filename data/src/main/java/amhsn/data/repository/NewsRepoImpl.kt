package amhsn.data.repository

import amhsn.data.local.ArticleDao
import amhsn.data.mapper.toNewsRequestData
import amhsn.data.mapper.toNewsResponseData
import amhsn.data.mapper.toNewsResponseDomain
import amhsn.data.remote.api_service.NewsAPI
import amhsn.domain.entities.NewsRequest
import amhsn.domain.entities.NewsResponse
import amhsn.domain.repository.NewsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepoImpl(
    private val newsApi: NewsAPI,
    private val articleDao: ArticleDao
) : NewsRepo {

    override suspend fun getNewsRemote(newsRequest: NewsRequest, page: Int): NewsResponse {
        return newsApi.getNews(newsRequest.toNewsRequestData(), page).toNewsResponseDomain()
    }

    override suspend fun insertNewsLocal(news: NewsResponse) {
        return articleDao.insert(news.toNewsResponseData())
    }

    override suspend fun getNewsLocal(): Flow<NewsResponse> {
      return articleDao.getNews().map { it.toNewsResponseDomain() }
    }

}