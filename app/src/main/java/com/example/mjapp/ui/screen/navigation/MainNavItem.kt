package com.example.mjapp.ui.screen.navigation

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

    object CalendarAdd: NavScreen(
        MainNavItem(
            route = "CalendarAdd",
            routeWithPostFix = "CalendarAdd/{date}"
        )
    ) {
        const val Date = "date"
    }
}