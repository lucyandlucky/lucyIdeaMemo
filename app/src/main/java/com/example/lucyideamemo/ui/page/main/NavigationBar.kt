package com.example.lucyideamemo.ui.page.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.moriafly.salt.ui.SaltTheme
import com.moriafly.salt.ui.Text
import java.util.Locale


@Composable
fun AdaptNavigationBar(
    destinations: List<NavigationBarPath>,
    currentDestination: String,
    onNavigateToDestination: (Int) -> Unit,
    isWideScreen: Boolean,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier, containerColor = SaltTheme.colors.subBackground) {
        destinations.forEachIndexed { index, destination ->
            val selected = destination.route == currentDestination
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(index) },
                icon = destination.icon,
//                label = { Text(destination.label()) }
            )
        }
    }
}

enum class NavigationBarPath(
    val route: String,
    val icon: @Composable () -> Unit,
    val label: @Composable () -> String,
) {
    AllNote(
        route = "home".capitalize(),
        icon = {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "home"
            )
        },
        label = { "Home" }
    ),
    Setting(
        route = "setting".capitalize(),
        icon = {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "settings"
            )
        },
        label = { "Settings" }
    )


}

private fun String.capitalize() =
    replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }