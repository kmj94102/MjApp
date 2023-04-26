package com.example.mjapp.ui.screen.navigation

data class MainNavItem(
    override val title: String,
    override val route: String,
    override val routeWithPostFix: String = route
) : NavItem