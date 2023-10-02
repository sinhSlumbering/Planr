package com.example.planr.navgraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.planr.screens.ForgotPassScreen
import com.example.planr.screens.LoginScreen
import com.example.planr.screens.SignUpScreen

fun NavGraphBuilder.AuthNavGraph(navController: NavHostController){
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login.route

    ){
        composable(route = AuthScreen.Login.route){
            LoginScreen(
                navController = navController,
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Graph.HOME)
                },
                onSignUpClick = {
                    navController.navigate(AuthScreen.SignUp.route)
                },
                onForgotClick = {
                    navController.navigate(AuthScreen.Forgot.route)
                },
            )
        }
    }
    composable(route = AuthScreen.SignUp.route){
        SignUpScreen(
            onClick = {
                navController.navigate(AuthScreen.Login.route)
            }
        )
    }
    composable(route = AuthScreen.Forgot.route){
        ForgotPassScreen (
            onClick = {
                navController.navigate(AuthScreen.Login.route)
            }
        )
    }
}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SIGN_UP")
    object Forgot : AuthScreen(route = "FORGOT")
}