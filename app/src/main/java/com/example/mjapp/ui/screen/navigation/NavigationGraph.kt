package com.example.mjapp.ui.screen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    val onBackClick: () -> Unit = { navController.popBackStack() }

    NavHost(
        navController = navController,
        startDestination = BottomNavItems.Home.item.routeWithPostFix
    ) {
        /** 홈 화면 **/
        composable(
            route = BottomNavItems.Home.item.routeWithPostFix
        ) {

        }
        /** 게임 화면 **/
        composable(
            route = BottomNavItems.Game.item.routeWithPostFix
        ) {

        }
        /** 달력 화면 **/
        composable(
            route = BottomNavItems.Calendar.item.routeWithPostFix
        ) {

        }
        /** 식물 화면 **/
        composable(
            route = BottomNavItems.Plant.item.routeWithPostFix
        ) {

        }
        /** 기타 화면 **/
        composable(
            route = BottomNavItems.Other.item.routeWithPostFix
        ) {

        }
    }
}