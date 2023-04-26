package com.example.mjapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mjapp.ui.screen.navigation.BottomNavItem
import com.example.mjapp.ui.screen.navigation.BottomNavItems
import com.example.mjapp.ui.screen.navigation.NavigationGraph
import com.example.mjapp.ui.theme.*
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16B
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MjAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
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
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(ColorBlack)

    Scaffold(
        backgroundColor = ColorWhite,
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                onClick = {
                    navController.navigate(it)
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavigationGraph(navController = navController)
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    onClick: (String) -> Unit
) {
    val items = BottomNavItems.values().map { it.item }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val isVisible = items
        .map { it.routeWithPostFix }
        .contains(backStackEntry?.destination?.route)

    if (isVisible) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, ColorBlack, RoundedCornerShape(10.dp))
                .background(ColorBeige)
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            items.forEach {
                MyBottomNavItem(
                    item = it,
                    selected = backStackEntry?.destination?.route == it.routeWithPostFix,
                    onClick = onClick
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Composable
fun RowScope.MyBottomNavItem(
    item: BottomNavItem,
    selected: Boolean,
    onClick: (String) -> Unit
) {
    val background = if (selected) ColorSkyBlue else Color.Transparent
    val border = BorderStroke(1.dp, if (selected) ColorBlack else Color.Transparent)
    val weight = animateFloatAsState(targetValue = if (selected) 1f else 0.4f)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .weight(weight.value)
            .heightIn(max = 47.dp)
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(border, RoundedCornerShape(10.dp))
            .background(background)
            .nonRippleClickable { onClick(item.routeWithPostFix) }
    ) {
        Image(
            painter = painterResource(id = item.icon),
            contentDescription = item.title,
            modifier = Modifier
                .padding(vertical = 5.dp)
                .size(27.dp)
        )
        AnimatedVisibility(visible = selected) {
            Text(
                text = item.title,
                style = textStyle16B(),
                modifier = Modifier.padding(start = 5.dp)
            )
        }
    }
}