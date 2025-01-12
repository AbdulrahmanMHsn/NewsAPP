package amhsn.domain.usecase

import amhsn.domain.NoInternetConnectionException
import amhsn.domain.entities.Article
import amhsn.domain.entities.NewsRequest
import amhsn.domain.repository.NewsRepo

class SearchUseCase(private val newsRepo: NewsRepo) {

    suspend operator fun invoke(newsRequest: NewsRequest, page: Int): Result<List<Article>> {
        return newsRepo.search(newsRequest, page)
    }
}