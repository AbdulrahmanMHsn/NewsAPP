package amhsn.newsapp.presentation.activity

import amhsn.newsapp.presentation.details.DetailsScreen
import amhsn.newsapp.presentation.news_list.NewsListScreen
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun Navigation(
    modifier: Modifier,
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {

    NavHost(
        navController = navController,
        startDestination = Screens.NewsList.route
    ) {

        composable(Screens.NewsList.route) {
            NewsListScreen(modifier = modifier, onItemClick = { url ->
                sharedViewModel.shareURL(url)
                navController.navigate(Screens.Details.route) {
                    launchSingleTop = true
                }
            })
        }

        composable(Screens.Details.route) {
            DetailsScreen(urlToRender = sharedViewModel.url.value)
        }

        composable("don't remove this otherwise application will crash") {}

    }
}