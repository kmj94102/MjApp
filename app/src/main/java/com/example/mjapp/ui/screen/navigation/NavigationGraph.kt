package com.example.mjapp.ui.screen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mjapp.ui.screen.calendar.CalendarScreen
import com.example.mjapp.ui.screen.game.GameScreen
import com.example.mjapp.ui.screen.home.HomeScreen
import com.example.mjapp.ui.screen.other.OtherScreen
import com.example.mjapp.ui.screen.plant.PlantScreen

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
            HomeScreen()
        }
        /** 게임 화면 **/
        composable(
            route = BottomNavItems.Game.item.routeWithPostFix
        ) {
            GameScreen()
        }
        /** 달력 화면 **/
        composable(
            route = BottomNavItems.Calendar.item.routeWithPostFix
        ) {
            CalendarScreen()
        }
        /** 식물 화면 **/
        composable(
            route = BottomNavItems.Plant.item.routeWithPostFix
        ) {
            PlantScreen()
        }
        /** 기타 화면 **/
        composable(
            route = BottomNavItems.Other.item.routeWithPostFix
        ) {
            OtherScreen()
        }
    }
}