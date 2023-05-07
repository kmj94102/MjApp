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
}