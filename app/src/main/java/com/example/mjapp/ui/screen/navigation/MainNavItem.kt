package com.example.mjapp.ui.screen.navigation

data class MainNavItem(
    override val title: String,
    override val route: String,
    override val routeWithPostFix: String = route
) : NavItem

sealed class NavScreen(val item: MainNavItem) {
    object ElswordIntroduce: NavScreen(
        MainNavItem(
            title = "엘소드 캐릭터 소개",
            route = "ElswordIntroduce"
        )
    )

    object ElswordCounter: NavScreen(
        MainNavItem(
            title = "엘소드 카운터",
            route = "ElswordCounter"
        )
    )

    object ElswordCounterAdd: NavScreen(
        MainNavItem(
            title = "엘소드 카운터 등록",
            route = "ElswordCounterAdd"
        )
    )

    object PokemonAdd: NavScreen(
        MainNavItem(
            title = "포켓몬 추가",
            route = "PokemonAdd"
        )
    )

    object PokemonDex: NavScreen(
        MainNavItem(
            title = "포켓몬 도감",
            route = "PokemonDex"
        )
    )

    object PokemonCounter: NavScreen(
        MainNavItem(
            title = "포케몬 카운터",
            route = "PokemonCounter"
        )
    )
}