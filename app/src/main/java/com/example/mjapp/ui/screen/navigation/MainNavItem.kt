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
}