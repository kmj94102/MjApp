package com.example.mjapp.ui.screen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mjapp.ui.screen.accountbook.AccountBookScreen
import com.example.mjapp.ui.screen.accountbook.fixed.RegistrationFixedAccountBookScreen
import com.example.mjapp.ui.screen.accountbook.add.AddNewAccountBookItemScreen
import com.example.mjapp.ui.screen.accountbook.detail.AccountBookDetailScreen
import com.example.mjapp.ui.screen.accountbook.fixed.AddFixedAccountBookScreen
import com.example.mjapp.ui.screen.calendar.CalendarScreen
import com.example.mjapp.ui.screen.calendar.add.PlanAddScreen
import com.example.mjapp.ui.screen.calendar.add.ScheduleAddScreen
import com.example.mjapp.ui.screen.game.GameScreen
import com.example.mjapp.ui.screen.game.elsword.counter.ElswordCounterScreen
import com.example.mjapp.ui.screen.game.elsword.counter.add.ElswordCounterAddScreen
import com.example.mjapp.ui.screen.game.elsword.introduce.ElswordIntroduceScreen
import com.example.mjapp.ui.screen.game.pokemon.add.PokemonAddScreen
import com.example.mjapp.ui.screen.game.pokemon.change.PokemonImageChangeScreen
import com.example.mjapp.ui.screen.game.pokemon.counter.PokemonCounterHistoryScreen
import com.example.mjapp.ui.screen.game.pokemon.counter.PokemonCounterScreen
import com.example.mjapp.ui.screen.game.pokemon.dex.PokemonDexScreen
import com.example.mjapp.ui.screen.home.HomeScreen
import com.example.mjapp.ui.screen.other.OtherScreen
import com.example.mjapp.ui.screen.other.english_words.EnglishWordsScreen
import com.example.mjapp.ui.screen.other.english_words.exam.ExamScreen
import com.example.mjapp.ui.screen.other.english_words.memorize.MemorizeScreen
import com.example.mjapp.ui.screen.other.english_words.wrong_answer.WrongAnswerScreen
import com.example.mjapp.ui.screen.other.internet.InternetFavoritesScreen
import com.example.mjapp.util.Constants
import com.example.mjapp.util.makeRouteWithArgs

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
        accountBookScreens(onBackClick, navController)
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
                val route = when (it) {
                    Constants.PokemonDex -> {
                        NavScreen.PokemonDex.item.routeWithPostFix
                    }
                    Constants.PokemonCounter -> {
                        NavScreen.PokemonCounter.item.routeWithPostFix
                    }
                    Constants.PokemonAdd -> {
                        NavScreen.PokemonAdd.item.routeWithPostFix
                    }
                    Constants.PokemonImageChange -> {
                        NavScreen.PokemonImageChange.item.routeWithPostFix
                    }
                    Constants.ElswordIntroduce -> {
                        NavScreen.ElswordIntroduce.item.routeWithPostFix
                    }
                    Constants.ElswordCounter -> {
                        NavScreen.ElswordCounter.item.routeWithPostFix
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
    /** 엘소드 카운터 **/
    composable(
        route = NavScreen.ElswordCounter.item.routeWithPostFix
    ) {
        ElswordCounterScreen(
            onBackClick = onBackClick,
            goToAdd = {
                navController.navigate(NavScreen.ElswordCounterAdd.item.routeWithPostFix)
            }
        )
    }
    /** 엘소드 카운터 등록 **/
    composable(
        route = NavScreen.ElswordCounterAdd.item.routeWithPostFix
    ) {
        ElswordCounterAddScreen(
            onBackClick = onBackClick
        )
    }
    /** 포켓몬 추가 화면 **/
    composable(
        route = NavScreen.PokemonAdd.item.routeWithPostFix
    ) {
        PokemonAddScreen(
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
    /** 포켓몬 카운터 화면 **/
    composable(
        route = NavScreen.PokemonCounter.item.routeWithPostFix
    ) {
        PokemonCounterScreen(
            onBackClick = onBackClick,
            goToHistory = {
                navController.navigate(NavScreen.PokemonCounterHistory.item.routeWithPostFix)
            }
        )
    }
    /** 포켓몬 카운터 히스토리 화면 **/
    composable(
        route = NavScreen.PokemonCounterHistory.item.routeWithPostFix
    ) {
        PokemonCounterHistoryScreen(
            onBackClick = onBackClick
        )
    }
    /** 포켓몬 이미지 수정 **/
    composable(
        route = NavScreen.PokemonImageChange.item.routeWithPostFix
    ) {
        PokemonImageChangeScreen(
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
        CalendarScreen(
            goToAdd = {
                navController.navigate(
                    makeRouteWithArgs(
                        NavScreen.ScheduleAdd.item.route,
                        it
                    )
                )
            }
        )
    }
    /** 일정 아이템 추가 화면 **/
    composable(
        route = NavScreen.ScheduleAdd.item.routeWithPostFix,
        arguments = listOf(
            navArgument(Constants.Date) { type = NavType.StringType }
        )
    ) {
        ScheduleAddScreen(
            onBackClick = onBackClick,
            goToPlanAdd = {
                navController.navigate(
                    makeRouteWithArgs(
                        NavScreen.PlanAdd.item.route,
                        it
                    )
                ) {
                    navController.popBackStack()
                }
            }
        )
    }
    /** 계획 아이템 추가 화면 **/
    composable(
        route = NavScreen.PlanAdd.item.routeWithPostFix,
        arguments = listOf(
            navArgument(Constants.Date) { type = NavType.StringType }
        )
    ) {
        PlanAddScreen(
            onBackClick = onBackClick,
            goToScheduleAdd = {
                navController.navigate(
                    makeRouteWithArgs(
                        NavScreen.ScheduleAdd.item.route,
                        it
                    )
                ) {
                    navController.popBackStack()
                }
            }
        )
    }
}

/** 가계부 관련 화면 **/
fun NavGraphBuilder.accountBookScreens(
    onBackClick: () -> Unit,
    navController: NavHostController
) {
    /** 가계부 화면 **/
    composable(
        route = BottomNavItems.AccountBook.item.routeWithPostFix
    ) {
        AccountBookScreen(
            goToNewAccountBookItem = {
                navController.navigate(
                    makeRouteWithArgs(
                        NavScreen.AddNewAccountBookItem.item.route,
                        it
                    )
                )
            },
            goToFixedAccountBookItem = {
                navController.navigate(
                    makeRouteWithArgs(
                        NavScreen.RegistrationFixedAccountBookItem.item.route,
                        it
                    )
                )
            },
            goToDetail = {
                navController.navigate(
                    makeRouteWithArgs(
                        NavScreen.AccountBookDetail.item.route,
                        it
                    )
                )
            }
        )
    }

    /** 수입/지출 추가 화면 **/
    composable(
        route = NavScreen.AddNewAccountBookItem.item.routeWithPostFix,
        arguments = listOf(
            navArgument(NavScreen.AddNewAccountBookItem.Date) { type = NavType.StringType }
        )
    ) {
        AddNewAccountBookItemScreen(onBackClick)
    }

    /** 고정 내역 등록 화면 **/
    composable(
        route = NavScreen.RegistrationFixedAccountBookItem.item.routeWithPostFix,
        arguments = listOf(
            navArgument(Constants.Date) { type = NavType.StringType }
        )
    ) {
        RegistrationFixedAccountBookScreen(
            onBackClick = onBackClick,
            goToAddFixed = {
                navController.navigate(NavScreen.AddFixedAccountBook.item.routeWithPostFix)
            }
        )
    }

    /** 고정 내역 추가 화면 **/
    composable(
        route = NavScreen.AddFixedAccountBook.item.routeWithPostFix
    ) {
        AddFixedAccountBookScreen(
            onBackClick = onBackClick
        )
    }

    /** 월별 가계부 상세 조회 **/
    composable(
        route = NavScreen.AccountBookDetail.item.routeWithPostFix,
        arguments = listOf(
            navArgument(NavScreen.AccountBookDetail.Date) { type = NavType.StringType }
        )
    ) {
        AccountBookDetailScreen(
            onBackClick = onBackClick,
            goToNewAccountBookItem = {
                navController.navigate(
                    makeRouteWithArgs(
                        NavScreen.AddNewAccountBookItem.item.route,
                        it
                    )
                )
            }
        )
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
        OtherScreen {
            navController.navigate(it)
        }
    }
    /** 인터넷 즐겨찾기 화면 **/
    composable(
        route = NavScreen.InternetFavorites.item.routeWithPostFix
    ) {
        InternetFavoritesScreen(
            onBackClick = onBackClick
        )
    }
    /** 영단어 암기 화면 **/
    composable(
        route = NavScreen.EnglishWords.item.routeWithPostFix
    ) {
        EnglishWordsScreen(
            onBackClick = onBackClick,
            goToScreen = { route, args ->
                navController.navigate(
                    makeRouteWithArgs(
                        route,
                        args
                    )
                )
            }
        )
    }
    /** 영단어 암기하기 화면 **/
    composable(
        route = NavScreen.Memorize.item.routeWithPostFix,
        arguments = listOf(
            navArgument(Constants.Day) { type = NavType.IntType }
        )
    ) {
        MemorizeScreen(
            onBackClick = onBackClick
        )
    }
    /** 영단어 테스트 화면 **/
    composable(
        route = NavScreen.Exam.item.routeWithPostFix,
        arguments = listOf(
            navArgument(Constants.Day) { type = NavType.IntType }
        )
    ) {
        ExamScreen(
            onBackClick = onBackClick,
            goToWrongAnswer = { day ->
                navController.navigate(
                    makeRouteWithArgs(
                        NavScreen.WrongAnswers.item.route,
                        day.toString()
                    )
                ) {
                    navController.popBackStack()
                }
            }
        )
    }
    /** 오답노트 화면 **/
    composable(
        route = NavScreen.WrongAnswers.item.routeWithPostFix,
        arguments = listOf(
            navArgument(Constants.Day) { type = NavType.IntType }
        )
    ) {
        WrongAnswerScreen(
            onBackClick = onBackClick
        )
    }
}