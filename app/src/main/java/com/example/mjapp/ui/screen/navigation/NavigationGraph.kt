package com.example.mjapp.ui.screen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mjapp.ui.custom.Constants
import com.example.mjapp.ui.screen.calendar.CalendarScreen
import com.example.mjapp.ui.screen.game.GameScreen
import com.example.mjapp.ui.screen.game.elsword.introduce.ElswordIntroduceScreen
import com.example.mjapp.ui.screen.game.pokemon.add.PokemonAddScreen
import com.example.mjapp.ui.screen.game.pokemon.dex.PokemonDexScreen
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
        gameScreens(onBackClick, navController)
        calendarScreens(onBackClick, navController)
        plantScreens(onBackClick, navController)
        otherScreens(onBackClick, navController)
    }
}

/** 게임 관련 화면 **/
fun NavGraphBuilder.gameScreens(
    onBackClick: () -> Unit,
    navController: NavHostController
) {
    /** 게임 화면 **/
    composable(
        route = BottomNavItems.Game.item.routeWithPostFix
    ) {
        GameScreen(
            goToScreen = {
                val route = when(it) {
                    Constants.PokemonDex -> {
                        NavScreen.PokemonDex.item.routeWithPostFix
                    }
                    Constants.PokemonCounter -> {
                        ""
                    }
                    Constants.PokemonAdd -> {
                        NavScreen.PokemonAdd.item.routeWithPostFix
                    }
                    Constants.ElswordIntroduce -> {
                        NavScreen.ElswordIntroduce.item.routeWithPostFix
                    }
                    Constants.ElswordCounter -> {
                        ""
                    }
                    else -> ""
                }
                if (route.isEmpty()) return@GameScreen

                navController.navigate(route)
            }
        )
    }
    /** 엘소드 캐릭터 소개 화면 **/
    composable(
        route = NavScreen.ElswordIntroduce.item.routeWithPostFix
    ) {
        ElswordIntroduceScreen(
            onBackClick = onBackClick
        )
    }
    /** 포켓몬 추가 화면 **/
    composable(
        route = NavScreen.PokemonAdd.item.routeWithPostFix
    ) {
        PokemonAddScreen (
            onBackClick = onBackClick
        )
    }
    /** 포켓몬 리스트 화면 **/
    composable(
        route = NavScreen.PokemonDex.item.routeWithPostFix
    ) {
        PokemonDexScreen(
            onBackClick = onBackClick
        )
    }
}

/** 달력 관련 화면 **/
fun NavGraphBuilder.calendarScreens(
    onBackClick: () -> Unit,
    navController: NavHostController
) {
    /** 달력 화면 **/
    composable(
        route = BottomNavItems.Calendar.item.routeWithPostFix
    ) {
        CalendarScreen()
    }
}

/** 식물 관련 화면 **/
fun NavGraphBuilder.plantScreens(
    onBackClick: () -> Unit,
    navController: NavHostController
) {
    /** 식물 화면 **/
    composable(
        route = BottomNavItems.Plant.item.routeWithPostFix
    ) {
        PlantScreen()
    }
}

/** 기타 관련 화면 **/
fun NavGraphBuilder.otherScreens(
    onBackClick: () -> Unit,
    navController: NavHostController
) {
    /** 기타 화면 **/
    composable(
        route = BottomNavItems.Other.item.routeWithPostFix
    ) {
        OtherScreen()
    }
}