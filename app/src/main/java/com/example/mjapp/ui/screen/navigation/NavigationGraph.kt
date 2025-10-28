package com.example.mjapp.ui.screen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mjapp.ui.screen.accountbook.AccountBookScreen
import com.example.mjapp.ui.screen.accountbook.add.AddNewAccountBookItemScreen
import com.example.mjapp.ui.screen.accountbook.detail.AccountBookDetailScreen
import com.example.mjapp.ui.screen.accountbook.fixed.SelectFixedAccountBookScreen
import com.example.mjapp.ui.screen.calendar.ScheduleScreen
import com.example.mjapp.ui.screen.calendar.add.ScheduleAddScreen
import com.example.mjapp.ui.screen.game.GameScreen
import com.example.mjapp.ui.screen.game.dmo.list.DmoUnionScreen
import com.example.mjapp.ui.screen.game.elsword.counter.ElswordCounterScreen
import com.example.mjapp.ui.screen.game.elsword.counter.add.ElswordCounterAddScreen
import com.example.mjapp.ui.screen.game.elsword.introduce.ElswordIntroduceScreen
import com.example.mjapp.ui.screen.game.persona.Persona3Screen
import com.example.mjapp.ui.screen.game.persona.community.Persona3CommunityScreen
import com.example.mjapp.ui.screen.game.persona.quest.Persona3QuestScreen
import com.example.mjapp.ui.screen.game.pokemon.add.PokemonAddScreen
import com.example.mjapp.ui.screen.game.pokemon.change.PokemonImageChangeScreen
import com.example.mjapp.ui.screen.game.pokemon.counter.history.PokemonCounterHistoryScreen
import com.example.mjapp.ui.screen.game.pokemon.counter.PokemonCounterScreen
import com.example.mjapp.ui.screen.game.pokemon.detail.PokemonDetailScreen
import com.example.mjapp.ui.screen.game.pokemon.dex.PokemonDexScreen
import com.example.mjapp.ui.screen.game.pokemon.generation.GenerationDetailScreen
import com.example.mjapp.ui.screen.game.pokemon.generation.GenerationDexScreen
import com.example.mjapp.ui.screen.game.pokemon.search.PokemonSearchScreen
import com.example.mjapp.ui.screen.home.HomeScreen
import com.example.mjapp.ui.screen.other.OtherScreen
import com.example.mjapp.ui.screen.other.internet.InternetFavoritesScreen
import com.example.mjapp.ui.screen.other.word.detail.WordDetailScreen
import com.example.mjapp.ui.screen.other.word.exam.ExamScreen
import com.example.mjapp.ui.screen.other.word.note.WordStudyScreen
import com.example.mjapp.ui.screen.other.word.wronganswer.WrongAnswerScreen
import com.example.mjapp.util.Constants
import com.example.mjapp.util.makeRouteWithArgs
import com.example.network.model.FixedAccountBook
import com.google.gson.Gson

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    val onBackClick: () -> Unit = { navController.popBackStack() }

    NavHost(
        navController = navController,
        startDestination = NavScreen2.Home
    ) {
        /** 홈 화면 **/
        composable<NavScreen2.Home> { HomeScreen(navController) }
        gameScreens(onBackClick, navController)
        calendarScreens(navController)
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
    composable<NavScreen2.Game> { GameScreen(navController) }
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

    /** 타이틀 도감 화면 **/
    composable(
        route = NavScreen.GenerationDex.item.routeWithPostFix
    ) {
        GenerationDexScreen(
            onBackClick = onBackClick,
            goToDetail = {
                navController.navigate(
                    makeRouteWithArgs(
                        NavScreen.GenerationDexDetail.item.route,
                        "$it"
                    )
                )
            }
        )
    }
    /** 타이틀 도감 상세 화면 **/
    composable(
        route = NavScreen.GenerationDexDetail.item.routeWithPostFix,
        arguments = listOf(
            navArgument(Constants.INDEX) { type = NavType.StringType }
        )
    ) {
        GenerationDetailScreen(
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


    /** 포켓몬 리스트 화면 **/
    composable<NavScreen2.PokemonDex> { PokemonDexScreen(navController) }
    /** 포켓몬 상세 화면 **/
    composable<NavScreen2.PokemonDetail> { PokemonDetailScreen(navController) }
    /** 포켓몬 카운터 화면 **/
    composable<NavScreen2.PokemonCounter> { PokemonCounterScreen(navController) }
    /** 포켓몬 카운터 히스토리 화면 **/
    composable<NavScreen2.PokemonCounterHistory> { PokemonCounterHistoryScreen(navController) }
    /** 포켓몬 검색 화면 **/
    composable<NavScreen2.PokemonSearch> { PokemonSearchScreen(navController) }
    /** 페로소나 커뮤 스케줄 화면 **/
    composable<NavScreen2.Persona3> { Persona3Screen(navController) }
    /** 페로소나 커뮤 진행도 화면 **/
    composable<NavScreen2.Persona3Community> { Persona3CommunityScreen(navController) }
    /** 페로소나 커뮤 퀘스트 화면 **/
    composable<NavScreen2.Persona3Quest> { Persona3QuestScreen(navController) }
    /** DMO 유니온 화면 **/
    composable<NavScreen2.DmoUnion> { DmoUnionScreen(navController) }
}

/** 달력 관련 화면 **/
fun NavGraphBuilder.calendarScreens(
    navController: NavHostController
) {
    /** 달력 화면 **/
    composable<NavScreen2.Schedule> { ScheduleScreen(navController) }
    /** 달력 내용 추가 화면 **/
    composable<NavScreen2.ScheduleAdd> { ScheduleAddScreen(navController) }
}

/** 가계부 관련 화면 **/
fun NavGraphBuilder.accountBookScreens(
    onBackClick: () -> Unit,
    navController: NavHostController
) {
    /** 월별 가계부 상세 조회 **/
    composable(
        route = NavScreen.AccountBookDetail.item.routeWithPostFix,
        arguments = listOf(
            navArgument(NavScreen.AccountBookDetail.DATE) { type = NavType.StringType }
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
            },
            goToFixedAccountBookItem = {
                navController.navigate(NavScreen.AddFixedAccountBook.item.routeWithPostFix)
            }
        )
    }

    /** 가계부 화면 **/
    composable<NavScreen2.AccountBook> { AccountBookScreen(navController) }
    /** 수입/지출 추가 화면 **/
    composable<NavScreen2.AddAccountBook> { entry ->
        val data = entry
            .savedStateHandle
            .get<String>(Constants.SELECT_AMOUNT_ITEM)

        AddNewAccountBookItemScreen(
            navController = navController,
            data = Gson().fromJson(data, FixedAccountBook::class.java)
        )
    }
    /** 고정 내역 선택 화면 **/
    composable<NavScreen2.SelectFixedAccountBook> {
        SelectFixedAccountBookScreen(navController)
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
        OtherScreen(navController) {
            navController.navigate(it)
        }
    }
    composable<NavScreen2.Other> {
        OtherScreen(navController) {
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
    /** 단어 암기 리스트 화면 **/
    composable<NavScreen2.WordStudy> { WordStudyScreen(navController) }
    /** 단어 상세 화면 **/
    composable<NavScreen2.WordDetail> { WordDetailScreen(navController) }
    /** 단어 테스트 화면 **/
    composable<NavScreen2.WordExam> { ExamScreen(navController) }
    /** 오답 노트 화면 **/
    composable<NavScreen2.WrongAnswer> { WrongAnswerScreen(navController) }

}