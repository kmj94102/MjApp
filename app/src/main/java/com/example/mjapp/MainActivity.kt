package com.example.mjapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mjapp.ui.screen.navigation.BottomNavItem
import com.example.mjapp.ui.screen.navigation.BottomNavItems
import com.example.mjapp.ui.screen.navigation.NavScreen2
import com.example.mjapp.ui.screen.navigation.NavigationGraph
import com.example.mjapp.ui.theme.*
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16B
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MjAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MyColorBlack
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .background(MyColorBlack)
    ) {
        NavigationGraph(navController = navController)
        BottomNavigationBar(
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val items = MyNavigation.items
    val isVisible = items
        .map { it.route }
        .contains(backStackEntry?.destination?.route?.split(".")?.last())

    if (isVisible) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(MyColorLightBlack)
        ) {
            items.forEach {
                MyBottomNavItem(
                    item = it,
                    isSelected = backStackEntry?.destination?.route?.split(".")?.last() == it.route,
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun RowScope.MyBottomNavItem(
    item: MyNavigation,
    isSelected: Boolean,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(vertical = 21.dp)
            .nonRippleClickable {
                navController.navigate(item.item)
            }
    ) {
        Icon(
            painter = painterResource(item.icon),
            contentDescription = null,
            tint = if (isSelected) MyColorDarkBlue else MyColorGray
        )
    }
}

data class MyNavigation(
    val item: NavScreen2,
    val route: String,
    @DrawableRes
    val icon: Int
) {
    companion object {
        val items = listOf(
            MyNavigation(
                item = NavScreen2.Home,
                route = NavScreen2.Home.toString(),
                icon = R.drawable.ic_home
            ),
            MyNavigation(
                item = NavScreen2.Game,
                route = NavScreen2.Game.toString(),
                icon = R.drawable.ic_game_pad
            ),
            MyNavigation(
                item = NavScreen2.Schedule,
                route = NavScreen2.Schedule.toString(),
                icon = R.drawable.ic_calendar
            ),
            MyNavigation(
                item = NavScreen2.AccountBook,
                route = NavScreen2.AccountBook.toString(),
                icon = R.drawable.ic_flower
            ),
            MyNavigation(
                item = NavScreen2.Other,
                route = NavScreen2.Other.toString(),
                icon = R.drawable.ic_other
            ),
        )
    }
}