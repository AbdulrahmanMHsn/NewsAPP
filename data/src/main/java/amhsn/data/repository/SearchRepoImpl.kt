package amhsn.data.repository

import amhsn.data.local.ArticleDao
import amhsn.data.mapper.toArticleDomain
import amhsn.data.mapper.toNewsRequestData
import amhsn.data.mapper.toNewsResponseData
import amhsn.data.mapper.toNewsResponseDomain
import amhsn.data.remote.api_service.NewsAPI
import amhsn.domain.NoInternetConnectionException
import amhsn.domain.entities.Article
import amhsn.domain.entities.NewsRequest
import amhsn.domain.entities.NewsResponse
import amhsn.domain.repository.NewsRepo
import amhsn.domain.repository.SearchRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class SearchRepoImpl(
    private val newsApi: NewsAPI,
    private val articleDao: ArticleDao
) : SearchRepo {

    override suspend fun search(newsRequest: NewsRequest, page: Int): Result<List<Article>> {
        return try {
            val articles = newsApi.search(
                newsRequest.toNewsRequestData(),
                page
            ).articleData.map { it.toArticleDomain() }
            Result.success(articles)
        } catch (e: UnknownHostException) {
            Result.failure(NoInternetConnectionException(e.message))
        } catch (e: SocketTimeoutException) {
            Result.failure(NoInternetConnectionException(e.message))
        }
    }

}