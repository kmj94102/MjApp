package com.example.mjapp.ui.screen.navigation

import com.example.mjapp.util.Constants

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

    object PokemonCounter: NavScreen(
        MainNavItem(
            route = "PokemonCounter"
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
            routeWithPostFix = "ScheduleAdd/{${Constants.Date}}"
        )
    )

    object PlanAdd: NavScreen(
        MainNavItem(
            route = "PlanAdd",
            routeWithPostFix = "PlanAdd/{${Constants.Date}}"
        )
    )

    object AddNewAccountBookItem: NavScreen(
        MainNavItem(
            route = "AddNewAccountBookItem",
            routeWithPostFix = "AddNewAccountBookItem/{date}"
        )
    ) {
        const val Date = "date"
    }

    object RegistrationFixedAccountBookItem: NavScreen(
        MainNavItem(
            route = "RegistrationFixedAccountBookItem",
            routeWithPostFix = "RegistrationFixedAccountBookItem/{${Constants.Date}}"
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
        const val Date = "date"
    }

    object InternetFavorites: NavScreen(
        MainNavItem(
            route = "InternetFavoritesScreen"
        )
    )
}