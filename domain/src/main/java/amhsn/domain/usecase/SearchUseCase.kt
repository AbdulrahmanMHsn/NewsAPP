package amhsn.domain.usecase

import amhsn.domain.entities.NewsRequest
import amhsn.domain.entities.NewsResponse
import amhsn.domain.repository.NewsRepo

class SearchUseCase(private val newsRepo: NewsRepo) {

    suspend operator fun invoke(newsRequest: NewsRequest, page: Int): NewsResponse {
        return newsRepo.search(newsRequest, page)
    }

}