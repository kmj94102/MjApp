package com.example.mjapp.ui.screen.navigation

import com.example.mjapp.util.Constants
import kotlinx.serialization.Serializable

data class MainNavItem(
    val route: String,
    val routeWithPostFix: String = route
)

sealed class NavScreen(val item: MainNavItem) {
    object ElswordIntroduce: NavScreen(
        MainNavItem(
            route = "ElswordIntroduce"
        )
    )

    object ElswordCounter: NavScreen(
        MainNavItem(
            route = "ElswordCounter"
        )
    )

    object ElswordCounterAdd: NavScreen(
        MainNavItem(
            route = "ElswordCounterAdd"
        )
    )

    object PokemonAdd: NavScreen(
        MainNavItem(
            route = "PokemonAdd"
        )
    )

    object PokemonDex: NavScreen(
        MainNavItem(
            route = "PokemonDex"
        )
    )

    object GenerationDex: NavScreen(
        MainNavItem(
            route = "GenerationDex"
        )
    )

    object GenerationDexDetail: NavScreen(
        MainNavItem(
            route = "GenerationDexDetail",
            routeWithPostFix = "GenerationDexDetail/{${Constants.INDEX}}"
        )
    )

    object PokemonCounter: NavScreen(
        MainNavItem(
            route = "PokemonCounter"
        )
    )

    object PokemonCounterHistory: NavScreen(
        MainNavItem(
            route = "PokemonCounterHistory"
        )
    )

    object PokemonImageChange: NavScreen(
        MainNavItem(
            route = "PokemonImageChange"
        )
    )

    object ScheduleAdd: NavScreen(
        MainNavItem(
            route = "ScheduleAdd",
            routeWithPostFix = "ScheduleAdd/{${Constants.DATE}}"
        )
    )

    object PlanAdd: NavScreen(
        MainNavItem(
            route = "PlanAdd",
            routeWithPostFix = "PlanAdd/{${Constants.DATE}}"
        )
    )

    object AddNewAccountBookItem: NavScreen(
        MainNavItem(
            route = "AddNewAccountBookItem",
            routeWithPostFix = "AddNewAccountBookItem/{date}"
        )
    ) {
        const val DATE = "date"
    }

    object RegistrationFixedAccountBookItem: NavScreen(
        MainNavItem(
            route = "RegistrationFixedAccountBookItem",
            routeWithPostFix = "RegistrationFixedAccountBookItem/{${Constants.DATE}}"
        )
    )

    object AddFixedAccountBook: NavScreen(
        MainNavItem(
            route = "AddFixedAccountBook"
        )
    )

    object AccountBookDetail: NavScreen(
        MainNavItem(
            route = "AccountBookDetail",
            routeWithPostFix = "AccountBookDetail/{date}"
        )
    ) {
        const val DATE = "date"
    }

    object InternetFavorites: NavScreen(
        MainNavItem(
            route = "InternetFavoritesScreen"
        )
    )

    object Note: NavScreen(
        MainNavItem(
            route = "NoteScreen",
            routeWithPostFix = "NoteScreen/{is_exam}"
        )
    ) {
        const val IS_EXAM = "is_exam"
    }

    object WordDetail: NavScreen(
        MainNavItem(
            route = "WordDetail",
            routeWithPostFix = "WordDetail/{${Constants.INDEX}}/{${Constants.TITLE}}"
        )
    )

    object WordExam: NavScreen(
        MainNavItem(
            route = "WordExample",
            routeWithPostFix = "WordExample/{${Constants.INDEX}}/{${Constants.TITLE}}"
        )
    )

}

sealed interface NavScreen2 {
    @Serializable
    data object Home: NavScreen2

    @Serializable
    data object Game: NavScreen2

    @Serializable
    data object Other: NavScreen2

    @Serializable
    data object PokemonDex: NavScreen2

    @Serializable
    data object PokemonCounter: NavScreen2

    @Serializable
    data class PokemonDetail(val number: String): NavScreen2

    @Serializable
    data class PokemonSearch(
        val name: String = "",
        val types: List<String> = emptyList(),
        val generations: List<String> = emptyList(),
        val registrations: String = ALL,
        val isCatch: String = ALL
    ): NavScreen2 {
        companion object {
            const val ALL = "all"
            const val IS_NOT_CATCH = "is_not_catch"
        }
    }

    @Serializable
    data object PokemonCounterHistory: NavScreen2

    @Serializable
    data object Persona3: NavScreen2

    @Serializable
    data object Persona3Community: NavScreen2

    @Serializable
    data object Persona3Quest: NavScreen2

    @Serializable
    data object DmoUnion: NavScreen2

    @Serializable
    data class DmoUnionDetail(val id: Int): NavScreen2

    @Serializable
    data object Schedule: NavScreen2

    @Serializable
    data class ScheduleAdd(
        val date: String
    ): NavScreen2

    @Serializable
    data object AccountBook: NavScreen2

    @Serializable
    data object AddAccountBook: NavScreen2

    @Serializable
    data object SelectFixedAccountBook: NavScreen2

    @Serializable
    data object WordStudy: NavScreen2

    @Serializable
    data class WordDetail(
        val id: Int,
        val title: String
    ): NavScreen2

    @Serializable
    data class WordExam(
        val index: Int,
        val title: String
    ): NavScreen2

    @Serializable
    data object WrongAnswer: NavScreen2
}