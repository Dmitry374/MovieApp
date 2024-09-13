package com.example.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.details.presentation.DetailsRoute
import com.example.favorites.presentation.FavoritesRoute
import com.example.movieapp.navigation.Screen.DETAILS_SCREEN
import com.example.movieapp.navigation.Screen.FAVOURITES_SCREEN
import com.example.movieapp.navigation.Screen.MOVIES_SCREEN
import com.example.movieapp.ui.HomeScreen
import com.example.movies.presentation.MoviesRoute

@Composable
fun RootNavigationGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.HOME
    ) {
        composable(route = Graph.HOME) {
            HomeScreen()
        }
    }
}

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = MOVIES_SCREEN,
    ) {
        composable(MOVIES_SCREEN) {
            MoviesRoute(
                onNavigateToDetails = { titleId ->
                    navController.navigate(route = Graph.DETAILS + "/$titleId")
                }
            )
        }
        composable(FAVOURITES_SCREEN) {
            FavoritesRoute(
                onNavigateToDetails = { titleId ->
                    navController.navigate(route = Graph.DETAILS + "/$titleId")
                }
            )
        }
        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS + "/{titleId}",
        startDestination = DETAILS_SCREEN
    ) {
        composable(DETAILS_SCREEN) {
            DetailsRoute(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val HOME = "home_graph"
    const val DETAILS = "details_graph"
}

object Screen {
    const val MOVIES_SCREEN = "movies_screen"
    const val FAVOURITES_SCREEN = "favorites_screen"
    const val DETAILS_SCREEN = "details_screen"
}
