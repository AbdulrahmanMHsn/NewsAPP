package amhsn.newsapp.presentation.news_list

import amhsn.domain.entities.Article
import amhsn.newsapp.R
import amhsn.newsapp.presentation.common_components.SimpleScaffold
import amhsn.newsapp.presentation.common_components.SimpleTopBar
import amhsn.newsapp.presentation.news_list.components.NewsItem
import amhsn.newsapp.presentation.news_list.components.ShimmerAnimationNewsItem
import amhsn.newsapp.presentation.theme.LightGray100
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Path
import android.provider.SyncStateContract
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems


@ExperimentalMaterialApi
@Composable
fun NewsListScreen(modifier: Modifier,onItemClick:(String)->Unit ,viewModel: NewsViewModel = viewModel()) {
    
//    val list = viewModel.article.value
    val activity = (LocalContext.current as? Activity)
    val list = viewModel.article.collectAsLazyPagingItems()

    Scaffold(topBar = { SimpleTopBar(title = stringResource(id = R.string.news)) }) {


        NewsList(modifier = modifier, list = list)
//        LazyColumn {
//            list?.let { list ->
//                items(list) {
//                    NewsItem(
//                        modifier.fillMaxWidth(),
//                        onCLickItem = { onItemClick(it.url!!) },
//                        article = it,
//                        onShareItem = {
//                            shareItem(it.url!!,activity!!)
//                        }
//                    )
//                }
//            }
//        }

//        if (viewModel.progress.value) {
//            LazyColumn {
//                items(10) {
//                    ShimmerAnimationNewsItem()
//                }
//            }
//        }

    }

}


fun shareItem(url:String,activity:Context){

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
fun NewsList(modifier: Modifier = Modifier, list: LazyPagingItems<Article>) {
    LazyColumn {
        items(list.itemCount) { index ->
            list[index]?.let { item ->
                NewsItem(
                    modifier.fillMaxWidth(),
                    onCLickItem = { Log.i("NewsListScreen", "NewsListScreen: Hello") },
                    article = item,
                    onShareItem = {}
                )
            }
        }

        if (list.loadState.append is LoadState.Error) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(60.dp),
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(text = stringResource(R.string.connection_issue))
                    Spacer(modifier = Modifier.size(10.dp))
                    Button(onClick = { list.retry() }, shape = CircleShape) {
                        Text(text = stringResource(R.string.retry))
                    }
                }
            }
        }
    }
}






