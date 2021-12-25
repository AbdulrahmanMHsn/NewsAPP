package amhsn.newsapp.presentation.activity

import amhsn.newsapp.R
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(
    val route: String,
    @StringRes val title: Int,
) {

    object NewsList : Screens(
        route = "news_list",
        title = R.string.news_list,
    )

    object Details : Screens(
        route = "details",
        title = R.string.details,
    )

    object Search : Screens(
        route = "search",
        title = R.string.search,
    )

}
