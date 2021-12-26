package amhsn.newsapp.presentation.common_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import amhsn.newsapp.R
import amhsn.newsapp.presentation.theme.BackGround

@Composable
fun NoInternetConnection(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                modifier = Modifier.size(100.dp),
                imageVector = Icons.Default.WifiOff,
                contentDescription = stringResource(R.string.no_internet_connection),
                tint = BackGround
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = stringResource(R.string.no_internet_connection),
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = stringResource(R.string.check_your_connection),
                style = MaterialTheme.typography.body1,
                color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
            )
            Spacer(modifier = Modifier.size(24.dp))
            OutlinedButton(
                onClick = onClick,
                border = BorderStroke(1.dp, BackGround)
            ) {
                Text(text = stringResource(R.string.refresh),color = BackGround)
            }
        }
    }
}

@Composable
fun SomethingWentWrong(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                modifier = Modifier.size(100.dp),
                imageVector = Icons.Default.Error,
                contentDescription = stringResource(R.string.something_went_wrong),
                tint = BackGround
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = stringResource(R.string.something_went_wrong),
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = stringResource(R.string.please_try_again),
                style = MaterialTheme.typography.body1,
                color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
            )
            Spacer(modifier = Modifier.size(24.dp))
            OutlinedButton(
                onClick = onClick,
                border = BorderStroke(1.dp, BackGround)
            ) {
                Text(text = stringResource(R.string.refresh),color = BackGround)
            }
        }
    }
}