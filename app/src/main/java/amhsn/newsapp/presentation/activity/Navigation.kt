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
) {

    NavHost(
        navController = navController,
        startDestination = Screens.NewsList.route
    ) {

        composable(Screens.NewsList.route) {
            NewsListScreen(modifier = modifier, onItemClick = { url ->
//                navController.navigate(Screens.Details.route) {
//                    launchSingleTop = true
//                }
            })
        }

        composable(Screens.Details.route,
            arguments = listOf(
//                navArgument("url") { type = NavType.StringType }
            )) {
            DetailsScreen("")
        }
//
//        composable(Screens.Search.route) {
//
//        }

        composable("don't remove this otherwise application will crash") {}

    }
}