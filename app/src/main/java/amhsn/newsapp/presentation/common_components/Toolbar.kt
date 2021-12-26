package amhsn.newsapp.presentation.common_components

import amhsn.newsapp.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun SimpleScaffold(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    title: String,
    error: String = "",
    onErrorShow: () -> Unit = {},
    onBackClick: () -> Unit,
    actionIcon: ImageVector? = null,
    onActionClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = title,
                onBackClick = onBackClick,
                actionIcon = actionIcon,
                onActionClick = onActionClick
            )
        }
    ) {
        if (error.isNotEmpty()) {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(message = error)
                onErrorShow()
            }
        }
        Divider(thickness = 1.dp)
        content()
    }

}

@Composable
fun TopBar(
    title: String,
    onBackClick: () -> Unit,
    actionIcon: ImageVector? = null,
    onActionClick: () -> Unit = {},
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        actions = {
            actionIcon?.let {
                IconButton(onClick = onActionClick) {
                    Icon(
                        imageVector = it, contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        },
        title = { Text(text = title, color = MaterialTheme.colors.onPrimary) },
        elevation = 0.dp,
        backgroundColor = colorResource(id = R.color.background)
    )
}


@Composable
fun SimpleTopBar(
    title: String,
    onActionClick:()->Unit
) {
    TopAppBar(
        actions = {

            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = null,
                    tint = Color.White
                )
            }

        },
        title = { Text(text = title, color = MaterialTheme.colors.onPrimary) },
        elevation = 0.dp,
        backgroundColor = colorResource(id = R.color.background)
    )
}