package amhsn.newsapp.presentation.search

import amhsn.data.paging.SearchPagingSource
import amhsn.domain.entities.Article
import amhsn.domain.entities.NewsRequest
import amhsn.domain.usecase.SearchUseCase
import amhsn.newsapp.R
import amhsn.newsapp.presentation.common_components.NoInternetConnection
import amhsn.newsapp.presentation.common_components.SomethingWentWrong
import amhsn.newsapp.presentation.news_list.NewsViewModel
import amhsn.newsapp.presentation.news_list.components.NewsItem
import amhsn.newsapp.presentation.theme.BackGround
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import androidx.paging.compose.LazyPagingItems
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.features.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchUseCase: SearchUseCase) : ViewModel() {


    var article: Flow<PagingData<Article>> by mutableStateOf(flow { })
        private set


    fun search(txtSearch:String) = viewModelScope.launch(Dispatchers.IO) {
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


        withContext(Dispatchers.Main)
        {
            article = flow
        }
    }

}




