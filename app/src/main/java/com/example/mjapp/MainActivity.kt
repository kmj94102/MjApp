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
            .background(MyColorBlack)
    ) {
        NavigationGraph(navController = navController)
        BottomNavigationBar(
            navController = navController,
            onClick = { value ->
                navController.navigate(value)
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = BottomNavItems.entries.map { it.item }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val isVisible = items
        .map { it.routeWithPostFix }
        .contains(backStackEntry?.destination?.route)

    if (isVisible) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, MyColorBlack, RoundedCornerShape(10.dp))
                .background(MyColorBeige)
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            items.forEach {
                MyBottomNavItem(
                    item = it,
                    selected = backStackEntry?.destination?.route == it.routeWithPostFix,
                    onClick = onClick,
                    onNav2Click = {
                        navController.navigate(it)
                    }
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
    onClick: (String) -> Unit,
    onNav2Click: (NavScreen2) -> Unit = {}
) {
    val background = if (selected) MyColorSkyBlue else Color.Transparent
    val border = BorderStroke(1.dp, if (selected) MyColorBlack else Color.Transparent)
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
            .nonRippleClickable {
                item.screen?.let { onNav2Click(it) } ?: onClick(item.routeWithPostFix)
            }
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