package com.example.mjapp.ui.screen.navigation

import androidx.annotation.DrawableRes
import com.example.mjapp.R

data class BottomNavItem(
    val title: String,
    val route: String,
    val routeWithPostFix: String = route,
    val screen: NavScreen2? = null,
    @DrawableRes
    val icon: Int
)

enum class BottomNavItems(val item: BottomNavItem) {
    Home(
        item = BottomNavItem(
            title = "홈",
            route = "Home",
            icon = R.drawable.ic_home
        )
    ),
    Game(
        item = BottomNavItem(
            title = "게임",
            route = "Game",
            icon = R.drawable.ic_game_pad
        )
    ),
    Calendar(
        item = BottomNavItem(
            title = "달력",
            route = "Schedule",
            screen = NavScreen2.Schedule,
            icon = R.drawable.ic_calendar
        )
    ),
    AccountBook(
        item = BottomNavItem(
            title = "가계부",
            route = "AccountBook",
            screen = NavScreen2.AccountBook,
            icon = R.drawable.ic_flower
        )
    ),
    Other(
        item = BottomNavItem(
            title = "기타",
            route = "Other",
            icon = R.drawable.ic_other
        )
    ),
}