package amhsn.newsapp.presentation.search

import amhsn.domain.entities.Article
import amhsn.domain.entities.NewsRequest
import amhsn.domain.usecase.SearchUseCase
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
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchUseCase: SearchUseCase) : ViewModel() {


    var article: Flow<PagingData<Article>> by mutableStateOf(flow { })
        private set


    fun search(txtSearch:String) = viewModelScope.launch{
        val flow = Pager(
            PagingConfig(
                pageSize = 5,
                enablePlaceholders = true,
            )
        ) {
            SearchPagingSource(
                searchUseCase,
                NewsRequest(country = "us",q = txtSearch)
            )
        }.flow.cachedIn(viewModelScope)
        withContext(Main)
        {
            article = flow
        }
    }

}




