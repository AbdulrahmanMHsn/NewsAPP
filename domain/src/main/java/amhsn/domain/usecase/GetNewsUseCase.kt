package amhsn.domain.usecase

import amhsn.domain.entities.Article
import amhsn.domain.entities.NewsRequest
import amhsn.domain.entities.NewsResponse
import amhsn.domain.repository.NewsRepo
import amhsn.domain.util.Response
import kotlinx.coroutines.flow.first

class GetNewsUseCase(private val newsRepo: NewsRepo) {



    suspend operator fun invoke(newsRequest: NewsRequest,page:Int) :NewsResponse{

        val response = newsRepo.getNewsRemote(newsRequest,page)
//
////        val mergeDate = oldDataLocal.articles + response.articles
//        newsRepo.insertNewsLocal(NewsResponse(articles = response.articles))
//        val oldDataLocal = newsRepo.getNewsLocal().first()

        return response

//        return runCatching {
//            val response = newsRepo.getNewsRemote(newsRequest,5)
//            val oldDataLocal = newsRepo.getNewsLocal().first()
//            val mergeDate = oldDataLocal.articles + response.articles
//            newsRepo.insertNewsLocal(NewsResponse(articles = mergeDate))
////            Response.Success(response)
//        }.getOrElse {
//            runCatching {
//                val response = newsRepo.getNewsRemote(newsRequest,5)
////                Response.Error(it, response)
//            }.getOrElse {
////                Response.Error(it, null)
//            }
//        }

    }
}