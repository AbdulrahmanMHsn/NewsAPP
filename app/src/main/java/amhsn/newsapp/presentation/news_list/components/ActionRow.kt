package amhsn.newsapp.presentation.news_list.components

import amhsn.newsapp.R
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.ActionsRow(
    actionIconSize: Dp,
    onShare: () -> Unit,
) {

        IconButton(
            modifier = Modifier.size(50.dp).align(Alignment.CenterEnd),
            onClick = {
                onShare()
            },
            content = {
                Icon(
                    imageVector = Icons.Default.Share,
                    tint = Color.Black,
                    contentDescription = "delete action",
                )
            }
        )

}