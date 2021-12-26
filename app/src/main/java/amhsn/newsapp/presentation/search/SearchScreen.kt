package amhsn.newsapp.presentation.search

import amhsn.domain.entities.Article
import amhsn.newsapp.presentation.common_components.NoInternetConnection
import amhsn.newsapp.presentation.common_components.SomethingWentWrong
import amhsn.newsapp.presentation.search.components.SearchItem
import amhsn.newsapp.presentation.search.components.SearchView
import amhsn.newsapp.presentation.theme.BackGround
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import io.ktor.client.features.*
import io.ktor.network.sockets.*
import java.net.UnknownHostException


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun SearchScreen(onBackPress: () -> Unit,onItemClick: (String) -> Unit, searchViewModel: SearchViewModel = hiltViewModel()) {

    val list = searchViewModel.article.collectAsLazyPagingItems()
    var txtSearch by remember { mutableStateOf("") }

    Column {

        SearchView(
            txtSearch,
            onTextSearchChange = {
                txtSearch = it
                if (txtSearch.length > 2) {
                    searchViewModel.search(txtSearch)
                }
            },
            onBackPress,
            onClear = { txtSearch = "" },
            onSearch = { searchViewModel.search(txtSearch) }
        )

        Divider(thickness = 1.dp)


        SearchList(list = list, onItemClick = onItemClick)
    }


    if (!NetworkState(list, searchViewModel)) {
        SearchList(list = list, onItemClick = onItemClick)
    }

    if (list.loadState.refresh is LoadState.Loading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                color = BackGround,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    if (list.loadState.refresh is LoadState.NotLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (list.itemSnapshotList.isNullOrEmpty()) {
                Text(text = "Empty List", modifier = Modifier.align(Alignment.Center))
            }
        }
    }

}


@ExperimentalMaterialApi
@Composable
fun SearchList(
    modifier: Modifier = Modifier,
    list: LazyPagingItems<Article>,
    onItemClick: (String) -> Unit,
) {
    LazyColumn {
        items(list.itemCount) { index ->
            list[index]?.let { item ->
                SearchItem(
                    article = item,
                    onItemClick = { onItemClick(item.url.toString()) },
                )
            }
        }

        if (list.loadState.append is LoadState.Error) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(60.dp),
                        imageVector = Icons.Default.CloudOff,
                        contentDescription = null,
                        tint = BackGround,
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(text = stringResource(amhsn.newsapp.R.string.connection_issue))
                    Spacer(modifier = Modifier.size(10.dp))
                    Button(
                        onClick = { list.retry() },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = BackGround,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = stringResource(amhsn.newsapp.R.string.retry))
                    }
                }
            }
        }

        if (list.loadState.append is LoadState.Loading) {
            item {
                Box(Modifier.fillMaxWidth()) {
                    CircularProgressIndicator(
                        color = BackGround,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}


@ExperimentalFoundationApi
@Composable
fun NetworkState(newsList: LazyPagingItems<Article>, viewModel: SearchViewModel): Boolean {
    return if (newsList.loadState.refresh is LoadState.Error) {
        when ((newsList.loadState.refresh as LoadState.Error).error) {
            is UnknownHostException -> {
                NoInternetConnection {
                    newsList.retry()
                }
                false
            }
            is ServerResponseException, is HttpRequestTimeoutException, is SocketTimeoutException -> {
                SomethingWentWrong {
                    newsList.retry()
                }
                false
            }
            else -> false
        }
    } else {
        true
    }
}