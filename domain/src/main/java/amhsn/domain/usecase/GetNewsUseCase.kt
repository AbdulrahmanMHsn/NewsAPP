package amhsn.domain.usecase

import amhsn.domain.entities.NewsRequest
import amhsn.domain.entities.NewsResponse
import amhsn.domain.repository.NewsRepo
import amhsn.domain.util.Response

class GetNewsUseCase(private val newsRepo: NewsRepo) {
    suspend operator fun invoke(newsRequest: NewsRequest) :Response<NewsResponse>{
        val response = newsRepo.getNews(newsRequest,5)
        return runCatching {
            Response.Success(response)
        }.getOrElse {
            runCatching {
                Response.Error(it, response)
            }.getOrElse {
                Response.Error(it, null)
            }
        }
    }

//    suspend operator fun invoke(newsRequest: NewsRequest,page:Int) :NewsResponse{
//        return newsRepo.getNews(newsRequest,page)
//    }
}