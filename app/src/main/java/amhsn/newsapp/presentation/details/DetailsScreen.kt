package amhsn.newsapp.presentation.details

import amhsn.newsapp.R
import amhsn.newsapp.presentation.common_components.SimpleScaffold
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun DetailsScreen(urlToRender: String, onBackClick: () -> Unit) {

    SimpleScaffold(title = stringResource(id = R.string.details), onBackClick = onBackClick) {
        AndroidView(factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                loadUrl(urlToRender)

            }
        }, update = {
            it.loadUrl(urlToRender)
        })
    }
}