package amhsn.newsapp.presentation.news_list

import amhsn.data.paging.ProductsPagingSource
import amhsn.data.repository.NewsRepoImpl
import amhsn.domain.entities.Article
import amhsn.domain.entities.NewsRequest
import amhsn.domain.usecase.GetNewsUseCase
import amhsn.domain.util.Response
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(private val getNewsUseCase: GetNewsUseCase = GetNewsUseCase(NewsRepoImpl())):ViewModel() {

    init {
        getNews()
    }

    var article:Flow<PagingData<Article>> = flow {  }

//    var article = mutableStateOf<List<Article>?>(null)
//    var progress = mutableStateOf(true)
//
//    private fun getNews()=viewModelScope.launch(Dispatchers.IO) {
//        when (val response = getNewsUseCase.invoke(NewsRequest("us"))) {
//            is Response.Success -> {
//                article.value = response.result.articles
//                progress.value = false
//            }
//            is Response.Error -> {
//                progress.value = false
//            }
//        }
//    }



    private fun getNews() = viewModelScope.launch(Dispatchers.IO) {
            val flow = Pager(
                PagingConfig(
                    pageSize = 5,
                    enablePlaceholders = true,
                )
            ) {
                ProductsPagingSource(
                    getNewsUseCase,
                    NewsRequest(category = "us")
                )
            }.flow.cachedIn(viewModelScope)

            withContext(Dispatchers.Main)
            {
                article = flow
            }
        }
    }


