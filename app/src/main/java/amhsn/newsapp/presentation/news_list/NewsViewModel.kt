package amhsn.newsapp.presentation.news_list

import amhsn.data.paging.ProductsPagingSource
import amhsn.data.repository.NewsRepoImpl
import amhsn.domain.entities.Article
import amhsn.domain.entities.NewsRequest
import amhsn.domain.usecase.GetNewsUseCase
import amhsn.domain.util.Response
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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


    private fun getNews() = viewModelScope.launch(Dispatchers.IO) {
            val flow = Pager(
                PagingConfig(
                    pageSize = 5,
                    enablePlaceholders = true,
                )
            ) {
                ProductsPagingSource(
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


