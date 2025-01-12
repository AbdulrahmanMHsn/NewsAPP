package amhsn.domain.usecase

import amhsn.domain.entities.Article
import amhsn.domain.entities.NewsRequest
import amhsn.domain.repository.SearchRepo

class SearchUseCase(private val searchRepo: SearchRepo) {

    suspend operator fun invoke(newsRequest: NewsRequest, page: Int): Result<List<Article>> {
        return searchRepo.search(newsRequest, page)
    }
}