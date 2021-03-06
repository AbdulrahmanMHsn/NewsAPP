package amhsn.newsapp.presentation.news_list

import amhsn.domain.entities.Article
import amhsn.newsapp.R
import amhsn.newsapp.presentation.common_components.NoInternetConnection
import amhsn.newsapp.presentation.common_components.SimpleTopBar
import amhsn.newsapp.presentation.common_components.SomethingWentWrong
import amhsn.newsapp.presentation.news_list.components.NewsItem
import amhsn.newsapp.presentation.news_list.components.ShimmerAnimationNewsItem
import amhsn.newsapp.presentation.theme.BackGround
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.ktor.client.features.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.delay
import java.net.UnknownHostException


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun NewsListScreen(
    modifier: Modifier,
    onItemClick: (String) -> Unit,
    onActionClick: () -> Unit,
    viewModel: NewsViewModel = hiltViewModel()
) {

    val activity = (LocalContext.current as Activity)
    val list = viewModel.article.collectAsLazyPagingItems()
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)


    Scaffold(topBar = {
        SimpleTopBar(
            title = stringResource(id = R.string.news),
            onActionClick = onActionClick
        )
    }) {

        SwipeRefresh(state = swipeRefreshState, onRefresh = {
            isRefreshing = true
            viewModel.getNews()
        }) {


            NewsList(modifier = modifier, list = list, onItemClick = onItemClick) {
                shareItem(it, activity)
            }

            if (!NetworkState(list, viewModel)) {
                NewsList(modifier = modifier, list = list, onItemClick = onItemClick) {
                    shareItem(it, activity)
                }
            }

            if (list.loadState.refresh is LoadState.Loading) {
                LazyColumn {
                    items(10) {
                        ShimmerAnimationNewsItem()
                    }
                }
            }
        }

        LaunchedEffect(isRefreshing) {
            if (isRefreshing) {
                delay(1000L)
                isRefreshing = false
            }
        }
    }

}


fun shareItem(url: String, activity: Context) {

    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            url
        )
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, "Check out this news")
    activity.startActivity(shareIntent)
}


@ExperimentalMaterialApi
@Composable
fun NewsList(
    modifier: Modifier = Modifier,
    list: LazyPagingItems<Article>,
    onItemClick: (String) -> Unit,
    onShareItem: (String) -> Unit
) {
    LazyColumn {
        items(list.itemCount) { index ->
            list[index]?.let { item ->
                NewsItem(
                    modifier.fillMaxWidth(),
                    onCLickItem = { onItemClick(item.url.toString()) },
                    article = item,
                    onShareItem = { onShareItem(item.urlToImage.toString()) }
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
                    Text(text = stringResource(R.string.connection_issue))
                    Spacer(modifier = Modifier.size(10.dp))
                    Button(
                        onClick = { list.retry() },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = BackGround,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = stringResource(R.string.retry))
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
fun NetworkState(newsList: LazyPagingItems<Article>, viewModel: NewsViewModel): Boolean {
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





