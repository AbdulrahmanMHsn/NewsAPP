package amhsn.newsapp.presentation.news_list

import amhsn.data.paging.NewsPagingSource
import amhsn.domain.entities.Article
import amhsn.domain.entities.NewsRequest
import amhsn.domain.usecase.GetNewsUseCase
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val getNewsUseCase: GetNewsUseCase):ViewModel() {

    init {
        getNews()
    }

    var article:Flow<PagingData<Article>> by mutableStateOf(flow {  })
    private set


    fun getNews() = viewModelScope.launch(Dispatchers.IO) {
            val flow = Pager(
                PagingConfig(
                    pageSize = 5,
                    enablePlaceholders = true,
                )
            ) {
                NewsPagingSource(
                    getNewsUseCase,
                    NewsRequest(country = "us")
                )
            }.flow.cachedIn(viewModelScope)

            withContext(Dispatchers.Main)
            {
                article = flow
            }
        }

    }


