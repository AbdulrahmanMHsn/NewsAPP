package amhsn.newsapp.presentation.details

import amhsn.newsapp.R
import amhsn.newsapp.presentation.common_components.SimpleScaffold
import amhsn.newsapp.presentation.theme.BackGround
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun DetailsScreen(urlToRender: String, onBackClick: () -> Unit) {

    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)
    val visibility = remember { mutableStateOf(true) }


    SimpleScaffold(title = stringResource(id = R.string.details), onBackClick = onBackClick) {

        SwipeRefresh(state =swipeRefreshState, onRefresh = { isRefreshing = true

        }) {
            AndroidView(factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    loadUrl(urlToRender)


                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(
                            view: WebView, url: String,
                            favicon: Bitmap?
                        ) {
                            visibility.value = true
                        }

                        override fun onPageFinished(
                            view: WebView, url: String
                        ) {
                            visibility.value = false
                        }

                        override fun onPageCommitVisible(view: WebView?, url: String?) {
                            super.onPageCommitVisible(view, url)
                            visibility.value = false
                        }


                    }
                }


            }, update = {
                it.loadUrl(urlToRender)
            })

            Box(Modifier.fillMaxSize()) {
                if (visibility.value) {
                    CircularProgressIndicator(
                        color = BackGround,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

    }
}

@Composable
fun WebViews(urlToRender: String, visibility: (Boolean) -> Unit) {
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(urlToRender)


            webViewClient = object : WebViewClient() {
                override fun onPageStarted(
                    view: WebView, url: String,
                    favicon: Bitmap?
                ) {
                    visibility(true)
                }

                override fun onPageFinished(
                    view: WebView, url: String
                ) {
                    visibility(false)
                }

                override fun onPageCommitVisible(view: WebView?, url: String?) {
                    super.onPageCommitVisible(view, url)
                    visibility(false)
                }


            }
        }


    }, update = {
        it.loadUrl(urlToRender)
    })
}

