package com.example.planr.navgraphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.planr.screens.homeScreen

@Composable
fun RootNavGraph(navController: NavHostController){
    NavHost(navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION,
        ){
        AuthNavGraph(navController = navController)

        composable(route=Graph.HOME){
            homeScreen()
        }
    }
}

object Graph{
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val DETAILS = "details_graph"
}