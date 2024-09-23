package com.example.movieapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.R
import com.example.movieapp.navigation.HomeNavGraph
import com.example.movieapp.navigation.Screen
import com.example.movieapp.ui.model.TopLevelRoute

@Composable
fun HomeScreen() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("home_screen_container"),
        bottomBar = {
            MoviesBottomBar(navController = navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HomeNavGraph(navController = navController)
        }
    }
}

@Composable
fun MoviesBottomBar(navController: NavHostController) {
    val menuItems = listOf(
        TopLevelRoute(
            route = Screen.MOVIES_SCREEN,
            icon = R.drawable.ic_movie,
            title = "Movies"
        ),
        TopLevelRoute(
            route = Screen.FAVOURITES_SCREEN,
            icon = R.drawable.ic_love,
            title = "Favorites"
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = menuItems.any { it.route == currentDestination?.route }

    if (bottomBarDestination) {
        NavigationBar(
            modifier = Modifier
                .testTag("bottom_bar")
        ) {
            menuItems.forEach { topLevelRoute ->

                NavigationBarItem(
                    label = {
                        Text(text = topLevelRoute.title)
                    },
                    icon = {
                        Icon(
                            painterResource(id = topLevelRoute.icon),
                            contentDescription = topLevelRoute.title
                        )
                    },
                    selected = currentDestination?.hierarchy?.any { it.route == topLevelRoute.route } == true,
                    onClick = {
                        navController.navigate(topLevelRoute.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
