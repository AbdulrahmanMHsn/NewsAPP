package amhsn.newsapp.presentation.activity


import amhsn.newsapp.presentation.theme.NewsAPPTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            App()
        }
    }
}


@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun App() {
    NewsAPPTheme {
        Surface(color = MaterialTheme.colors.surface) {

            val navController = rememberNavController()
            val scaffoldState = rememberScaffoldState()
            val sharedViewModel: SharedViewModel = viewModel()

            Scaffold(
                scaffoldState = scaffoldState,
            ) { innerPadding ->
                Navigation(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                    sharedViewModel
                )
            }
        }
    }

}