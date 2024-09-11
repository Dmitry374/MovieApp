package com.example.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.navigation.Screen.FAVOURITES_SCREEN
import com.example.movieapp.navigation.Screen.MOVIES_SCREEN
import com.example.movieapp.ui.HomeScreen
import com.example.movies.presentation.FavouritesScreen
import com.example.movies.presentation.MoviesScreen

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
            MoviesScreen()
        }
        composable(FAVOURITES_SCREEN) {
            FavouritesScreen()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val HOME = "home_graph"
}

object Screen {
    const val MOVIES_SCREEN = "movies_screen"
    const val FAVOURITES_SCREEN = "favorites_screen"
}
