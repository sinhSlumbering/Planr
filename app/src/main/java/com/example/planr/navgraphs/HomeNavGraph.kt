package com.example.planr.navgraphs

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.planr.BottomBarScreen
import com.example.planr.fragments.ManageFragment
import com.example.planr.fragments.pomodoro
import com.example.planr.fragments.taskScreen
import com.example.planr.screens.DebugScreen
import com.example.planr.viewmodels.ManageViewModel
import com.example.planr.viewmodels.TaskScreenViewModel

//import com.example.planr.viewmodels.PomodoroTimerViewModel


@Composable
fun HomeNavGraph(navController: NavHostController){
    NavHost(navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ){
        profileNavGraph(navController = navController)
        composable(route = BottomBarScreen.Home.route){
            val tsViewModel = hiltViewModel<TaskScreenViewModel>()
            taskScreen(
                tsViewModel,
                onClickProfile = {
                    navController.navigate(Graph.DETAILS)
                }
            )
        }
        composable(route = BottomBarScreen.Pomodoro.route){
//            val pomodoroTimerViewModel = hiltViewModel<PomodoroTimerViewModel>()
            pomodoro()
        }
        composable(BottomBarScreen.Manage.route){
            val manageViewModel = hiltViewModel<ManageViewModel>()
            ManageFragment(manViewModel = manageViewModel)
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