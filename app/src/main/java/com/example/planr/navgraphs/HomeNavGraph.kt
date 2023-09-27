package com.example.planr.navgraphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.planr.BottomBarScreen
import com.example.planr.fragments.taskScreen
import com.example.planr.screens.DebugScreen

@Composable
fun HomeNavGraph(navController: NavHostController){
    NavHost(navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ){
        composable(route = BottomBarScreen.Home.route){
            taskScreen(
                onClickProfile = {
                    navController.navigate(Graph.DETAILS)
                }
            )
        }
        composable(route = BottomBarScreen.Pomodoro.route){
            DebugScreen(name = "Pomodoro") {
                
            }
        }
        composable(BottomBarScreen.Settings.route){
            DebugScreen(name = "Settings") {
                
            }
        }

    }
}

fun NavGraphBuilder.profileNavGraph(navController: NavHostController){
    navigation(
        route = Graph.DETAILS,
        startDestination = ProfileScreen.Information.route
    ){
        composable(route = ProfileScreen.Information.route){
            DebugScreen(name = ProfileScreen.Information.route) {
                navController.navigate(ProfileScreen.Overview.route)
            }
        }
        composable(route = ProfileScreen.Overview.route){
            DebugScreen(name = ProfileScreen.Overview.route) {
                navController.popBackStack(
                    route = ProfileScreen.Information.route,
                    inclusive = false,
                )
            }
        }
    }
}

sealed class ProfileScreen(val route: String) {
    object Information : ProfileScreen(route = "INFORMATION")
    object Overview : ProfileScreen(route = "EDIT")
}